package com.startrip.codebase.service;

import com.startrip.codebase.domain.place.Place;
import com.startrip.codebase.domain.place.PlaceRepository;
import com.startrip.codebase.dto.PlaceDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class InitPlaceCurationService implements CommandLineRunner {

    private final String FILE_INIT_SAMPLE = "config/init_sample.json";
    private final PlaceRepository placeRepository;
    private PlaceService placeService;

    @Override
    public void run(String... args) throws Exception {

        ClassPathResource resource;
        /* String [] regions = {"울산", "서울", "세종", "인천", "부산", "광주", "대전", "대구", "제주도", "강원도", "경기도", "경상남도", "경상북도"
        , "경상남도", "전라남도", "전라북도", "충청북도", "충청남도"}; */  // build time: 10m

        String [] regions = {"울산", "세종", "경기도"};
        for(String region : regions){
            resource = new ClassPathResource("xml/" + region + ".xml");
            getXmlDataToPlace(new File(resource.getURI()));
        }
    }

    private void getXmlDataToPlace(File xmlFile) throws Exception {

        DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = builderFactory.newDocumentBuilder();
        Document document = builder.parse(xmlFile);
        document.getDocumentElement().normalize();
        NodeList itemTagList = document.getElementsByTagName("item");

        List<Place> placeList = new ArrayList<>();
        log.info(xmlFile.getName() + itemTagList.getLength());

        for (int i = 0; i < itemTagList.getLength(); ++i) {
            Node node = itemTagList.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                StringBuilder address = new StringBuilder();
                String placeName = "";
                String imageUrl = "";
                String x = "";
                String y = "";

                Element element = (Element) node;
                address.append(getTagValue("addr1", element));
                address.append(getTagValue("addr2", element));
                placeName = getTagValue("title", element);
                imageUrl = getTagValue("firstimage", element);
                x = getTagValue("mapx", element);
                y = getTagValue("mapy", element);

                Place place;

                try {
                     place = Place.builder()
                            .id(UUID.randomUUID())
                            .placeName(placeName)
                            .address(address.toString())
                            .categoryId(UUID.randomUUID())
                            .latitude(Double.parseDouble(x))
                            .longitude(Double.parseDouble(y))
                            .placePhoto(imageUrl)
                            .build();
                }catch (NumberFormatException e){
                     place = Place.builder()
                            .id(UUID.randomUUID())
                            .placeName(placeName)
                            .address(address.toString())
                            .categoryId(UUID.randomUUID())
                            .latitude(0.0)
                            .longitude(0.0)
                            .placePhoto(imageUrl)
                            .build();
                }
                placeList.add(place);

            }
        }
        placeRepository.saveAll(placeList);
    }

    private static String getTagValue(String tag, Element eElement) {
        try {
            NodeList nlList = eElement.getElementsByTagName(tag).item(0).getChildNodes();
            Node nValue = (Node) nlList.item(0);
            if (nValue == null)
                return "";
            return nValue.getNodeValue();
        } catch (Exception e) {
            return "";
        }
    }

}
