package com.com304.service;

import com.com304.entity.ImgBoard;
import com.com304.entity.Menu;
import com.com304.repository.MenuRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.util.StringUtils;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class MenuService {

    @Value("${menuImgLocation}")
    private String boardImgLocation;

    private final MenuRepository menuRepository;

    private final FileService fileService;

    public void register(Menu menu, List<MultipartFile> itemImgFileList) throws Exception{

        for(int i=0;i<itemImgFileList.size();i++){
            saveItemImg(menu, itemImgFileList.get(i));
        }

    }

    public void saveItemImg(Menu menu, MultipartFile itemImgFile) throws Exception{
        String oriImgName = itemImgFile.getOriginalFilename();
        String imgName = "";
        String imgUrl = "";

        if(!StringUtils.isEmpty(oriImgName)){
            imgName = fileService.uploadFile(boardImgLocation, oriImgName,
                    itemImgFile.getBytes());
            imgUrl = "/images/food/menu/Img/" + imgName;
        }

        menu.updateImg(oriImgName, imgName, imgUrl);
        menuRepository.save(menu);
    }

    public void delete(Long meno){
        menuRepository.deleteById(meno);
    }

    public List<Menu> list(Long fno){
        List<Menu> menu = menuRepository.findByFno(fno);

        return menu;
    }
}

