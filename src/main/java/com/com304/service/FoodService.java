package com.com304.service;

import com.com304.dto.BoardDTO;
import com.com304.dto.FoodDTO;
import com.com304.dto.PageRequestDTO;
import com.com304.dto.PageResponseDTO;
import com.com304.entity.Board;
import com.com304.entity.Food;
import com.com304.entity.ImgBoard;
import com.com304.repository.FoodRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.util.StringUtils;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FoodService {

    @Value("${foodImgLocation}")
    private String foodImgLocation;

    private final ModelMapper modelMapper;

    private final FoodRepository foodRepository;

    private final FileService fileService;


    public void register(FoodDTO foodDTO, List<MultipartFile> itemImgFileList) throws Exception {
        Food food = modelMapper.map(foodDTO, Food.class);

        for(int i=0;i<itemImgFileList.size();i++){
            saveItemImg(food, itemImgFileList.get(i));
        }
    }

    public void saveItemImg(Food food, MultipartFile itemImgFile) throws Exception{
        String oriImgName = itemImgFile.getOriginalFilename();
        String imgName = "";
        String imgUrl = "";

        if(!StringUtils.isEmpty(oriImgName)){
            imgName = fileService.uploadFile(foodImgLocation, oriImgName,
                    itemImgFile.getBytes());
            imgUrl = "/images/food/Img/" + imgName;
        }

        food.updateImg(oriImgName, imgName, imgUrl);
        foodRepository.save(food);
    }


    public FoodDTO readOne(Long fno){
        Optional<Food> result = foodRepository.findById(fno);

        Food food = result.orElseThrow();

        FoodDTO foodDTO = modelMapper.map(food, FoodDTO.class);

        return foodDTO;
    }

    public void delete(Long fno){
        foodRepository.deleteById(fno);
    }

    public void modify(FoodDTO foodDTO){

        Optional<Food> result = foodRepository.findById(foodDTO.getFno());

        Food food = result.orElseThrow();

        food.change( foodDTO.getIntro(), foodDTO.getContact1(), foodDTO.getContact2(), foodDTO.getContact3(), foodDTO.getClose(), foodDTO.getTimeOpen(), foodDTO.getTimeClose());

        foodRepository.save(food);

    }

    public List<Food> getList(String cate){
        return foodRepository.findByCate(cate);
    }
}
