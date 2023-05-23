package com.com304.service;

import com.com304.dto.BoardDTO;
import com.com304.dto.ImgBoardDTO;
import com.com304.dto.PageRequestDTO;
import com.com304.dto.PageResponseDTO;
import com.com304.entity.Board;
import com.com304.entity.ImgBoard;
import com.com304.repository.ImgBoardRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.util.StringUtils;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class ImgBoardService {

    @Value("${boardImgLocation}")
    private String boardImgLocation;

    private final ModelMapper modelMapper;

    private final ImgBoardRepository imgBoardRepository;

    private final FileService fileService;

    public void register(ImgBoardDTO imgBoardDTO, List<MultipartFile> itemImgFileList) throws Exception {

        ImgBoard imgBoard = modelMapper.map(imgBoardDTO, ImgBoard.class);

        for(int i=0;i<itemImgFileList.size();i++){
            saveItemImg(imgBoard, itemImgFileList.get(i));
        }
    }

    public void saveItemImg(ImgBoard imgBoard, MultipartFile itemImgFile) throws Exception{
        String oriImgName = itemImgFile.getOriginalFilename();
        String imgName = "";
        String imgUrl = "";

        if(!StringUtils.isEmpty(oriImgName)){
            imgName = fileService.uploadFile(boardImgLocation, oriImgName,
                    itemImgFile.getBytes());
            imgUrl = "/images/boardImg/" + imgName;
        }

        imgBoard.updateImg(oriImgName, imgName, imgUrl);
        imgBoardRepository.save(imgBoard);
    }

    public PageResponseDTO<ImgBoardDTO> list(PageRequestDTO pageRequestDTO) {

        String[] types = pageRequestDTO.getTypes();
        String keyword = pageRequestDTO.getKeyword();
        Pageable pageable = pageRequestDTO.getPageable("bno");

        Page<ImgBoard> result = imgBoardRepository.searchAll(types, keyword, pageable);

        List<ImgBoardDTO> dtoList = result.getContent().stream()
                .map(imgBoard -> modelMapper.map(imgBoard,ImgBoardDTO.class)).collect(Collectors.toList());


        return PageResponseDTO.<ImgBoardDTO>withAll()
                .pageRequestDTO(pageRequestDTO)
                .dtoList(dtoList)
                .total((int)result.getTotalElements())
                .build();
    }

    public ImgBoardDTO readOne(Long bno){
        Optional<ImgBoard> result = imgBoardRepository.findById(bno);

        ImgBoard imgBoard = result.orElseThrow();

        ImgBoardDTO imgBoardDTO = modelMapper.map(imgBoard, ImgBoardDTO.class);

        return imgBoardDTO;
    }

    public void modify(ImgBoardDTO imgBoardDTO, List<MultipartFile> itemImgFileList) throws Exception {

        Optional<ImgBoard> result = imgBoardRepository.findById(imgBoardDTO.getBno());

        ImgBoard imgBoard = result.orElseThrow();

        imgBoard.change(imgBoardDTO.getTitle(),imgBoardDTO.getContent());

        if(itemImgFileList.get(0).getOriginalFilename().length() > 4) {
            for (int i = 0; i < itemImgFileList.size(); i++) {
                saveItemImg(imgBoard, itemImgFileList.get(i));
            }
        }

        System.out.println(itemImgFileList.get(0).getOriginalFilename().toString());
        System.out.println(itemImgFileList.get(0).getBytes());
        System.out.println(itemImgFileList.get(0).getInputStream());
        System.out.println(itemImgFileList.get(0).getResource());

        imgBoardRepository.save(imgBoard);
    }

    public void remove(Long bno){
        imgBoardRepository.deleteById(bno);
    }
}
