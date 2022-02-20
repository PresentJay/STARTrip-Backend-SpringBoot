package com.startrip.codebase.controller;

import com.startrip.codebase.domain.place.Place;
import com.startrip.codebase.service.PlaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;


@RestController
@ControllerAdvice
@RequestMapping("/place")
public class PlaceController {

    private PlaceService placeService;

    @Autowired
    public PlaceController(PlaceService placeService){
        this.placeService = placeService;
    }

    //sort
    @GetMapping("/sort/viewtime/DESC")
    public @ResponseBody ResponseEntity allSortBySort(){
        try {
            List<Place> placeList = this.placeService.allSortBySort();
            return new ResponseEntity(placeList, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/sort/nameASC-viewtimeDESC")
    public @ResponseBody ResponseEntity allSortListOrder(){
        try {
            List<Place> placeList = this.placeService.allSortListOrder();
            return new ResponseEntity(placeList, HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/list")
    // 파라미터로 model과 page를 가짐, 이때 @requestParam을 통해서 필수조건인지, 디폴트 값은 무엇인지 등을 설정함)
    public @ResponseBody ResponseEntity list(Model model, @RequestParam(required = false, defaultValue= "0", value = "page") int page){

        try {
            Page<Place> listPage = placeService.list(page); // 보여줄  페이지
            int totalPage = listPage.getTotalPages(); // totalPage 정보 뺴오기

            List<Place> currentListPage = listPage.getContent(); //
        /*
        // 이 model은 View에 데이터를 넘겨주는 방식임
        model.addAttribute("placeName", listPage.getContent()); //placeName이름으로 현재 검색한 데이터만 List로 반환시킴
        model.addAttribute("totalPage", totalPage); // totalPage이름으로 총 개수를 세서 넘겨줌
        */
            return new ResponseEntity(currentListPage, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
