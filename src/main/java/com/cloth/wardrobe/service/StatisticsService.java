package com.cloth.wardrobe.service;

import com.cloth.wardrobe.web.auth.dto.SessionMember;
import com.cloth.wardrobe.entity.clothes.Cloth;
import com.cloth.wardrobe.entity.member.Member;
import com.cloth.wardrobe.entity.member.MemberRepository;
import com.cloth.wardrobe.entity.statistics.StatisticsType;
import com.cloth.wardrobe.dto.statistics.ContentForStatistics;
import com.cloth.wardrobe.dto.statistics.ResponseForStatistics;
import com.cloth.wardrobe.exception.BadRequestException;
import com.cloth.wardrobe.repository.ClothRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class StatisticsService {

    private final ClothRepository clothRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public ResponseEntity<ResponseForStatistics> findStatistics(Optional<SessionMember> sessionMember) {
        HashMap<String, Integer> brandMap = new HashMap<>();
        HashMap<String, Integer> typeMap = new HashMap<>();
        HashMap<String, Integer> buyingTypeMap = new HashMap<>();
        HashMap<String, Integer> colorMap = new HashMap<>();

        List<Cloth> clothes;
        if(sessionMember.isEmpty()) {
            clothes = clothRepository.findAll();
        }
        else {
            Member member = memberRepository.findByEmail(sessionMember.get().getEmail()).orElseThrow(() -> new BadRequestException("올바르지 않은 사용자 입니다."));
            clothes = clothRepository.findClothsByMemberOrderByLikeCntDesc(member);
        }

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
        int total = clothes.size();
        addContentForStatistic(total, StatisticsType.Brand, brandSortedKeySet, contentForStatistics, brandMap);
        addContentForStatistic(total, StatisticsType.Color, colorSortedKeySet, contentForStatistics, colorMap);
        addContentForStatistic(total, StatisticsType.BuyingType, buyingTypeSortedKeySet, contentForStatistics, buyingTypeMap);
        addContentForStatistic(total, StatisticsType.Type, typeSortedKeySet, contentForStatistics, typeMap);

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

    private double getPercent(int total, int cnt) {
        return (double) cnt / total * 100;
    }

    private void addContentForStatistic(int total, StatisticsType type, String[] keySet, List<ContentForStatistics> contentForStatistics, Map<String, Integer> map) {
        int length = Math.min(keySet.length, 5);
        for(int i=0; i<length; i++) {
            contentForStatistics.add(new ContentForStatistics(
                    keySet[i],
                    type,
                    getPercent(total ,map.get(keySet[i])),
                    Integer.toUnsignedLong(map.get(keySet[i]))));
        }
    }

}
