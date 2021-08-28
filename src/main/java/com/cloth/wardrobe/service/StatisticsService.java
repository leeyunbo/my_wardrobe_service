package com.cloth.wardrobe.service;

import com.cloth.wardrobe.domain.clothes.Cloth;
import com.cloth.wardrobe.domain.statistics.StatisticsType;
import com.cloth.wardrobe.dto.statistics.ContentForStatistics;
import com.cloth.wardrobe.dto.statistics.ResponseForStatistics;
import com.cloth.wardrobe.repository.ClothRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class StatisticsService {

    private final ClothRepository clothRepository;

    @Transactional
    public ResponseEntity<ResponseForStatistics> findStatistics() {
        HashMap<String, Integer> brandMap = new HashMap<>();
        HashMap<String, Integer> typeMap = new HashMap<>();
        HashMap<String, Integer> buyingTypeMap = new HashMap<>();
        HashMap<String, Integer> colorMap = new HashMap<>();

        List<Cloth> clothes = clothRepository.findAll();
        for(Cloth cloth : clothes) {
            brandMap.put(cloth.getClothBrand(), brandMap.getOrDefault(cloth.getClothBrand(),0) + 1);
            typeMap.put(cloth.getClothType(), typeMap.getOrDefault(cloth.getClothType(), 0) + 1);
            buyingTypeMap.put(cloth.getBuyingWay(), buyingTypeMap.getOrDefault(cloth.getBuyingWay(), 0) + 1);
            colorMap.put(cloth.getClothColor(), colorMap.getOrDefault(cloth.getClothColor(), 0) + 1);
        }

        String[] brandSortedKeySet = sortByMap(brandMap);
        String[] typeSortedKeySet = sortByMap(typeMap);
        String[] buyingTypeSortedKeySet = sortByMap(buyingTypeMap);
        String[] colorSortedKeySet = sortByMap(colorMap);

        ResponseForStatistics responseForStatistics = new ResponseForStatistics();

        List<ContentForStatistics> contentForStatistics = new ArrayList<>();
        addContentForStatistic(StatisticsType.Brand, brandSortedKeySet, contentForStatistics, brandMap);
        addContentForStatistic(StatisticsType.Color, colorSortedKeySet, contentForStatistics, colorMap);
        addContentForStatistic(StatisticsType.BuyingType, buyingTypeSortedKeySet, contentForStatistics, buyingTypeMap);
        addContentForStatistic(StatisticsType.Type, typeSortedKeySet, contentForStatistics, typeMap);

        responseForStatistics.setContent(contentForStatistics);
        responseForStatistics.set_code(200);
        responseForStatistics.set_message("OK");

        return new ResponseEntity<>(responseForStatistics, HttpStatus.OK);
    }

    private String[] sortByMap(HashMap<String, Integer> map) {
        return map.keySet()
                .stream()
                .sorted((o1, o2) -> -1 * map.get(o1).compareTo(map.get(o2)))
                .toArray(String[]::new);
    }

    private int getPercent(int total, int cnt) {
        return (cnt/total) * 100;
    }

    private void addContentForStatistic(StatisticsType type, String[] keyset, List<ContentForStatistics> contentForStatistics, Map<String, Integer> map) {
        int length = Math.min(keyset.length, 5);
        for(int i=0; i<length; i++) {
            contentForStatistics.add(new ContentForStatistics(
                    keyset[i],
                    type,
                    getPercent(keyset.length ,map.get(keyset[i])),
                    map.get(keyset[i])));
        }
    }

}
