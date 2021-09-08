## 옷장 관련 API 
### 옷장 리스트 가져오기 
```
/api/v1/wardrobes?page=1&pageSize=10 
```

|메서드|요청 URL|
|------|---|
|GET|api/v1/wardrobes|

#### Request Parameters 
|파라미터|타입|필수 여부|설명|
|------|---|---|---|
|page_number|Integer|필수|Pagination Index로 페이지 시작 번호는 `1`로 한다.|
|page_size|Integer|필수|Pagination Index로 페이지 시작 번호는 `1`로 한다.|


#### Response Parameters 
|파라미터|타입|필수 여부|설명|
|------|---|---|---|
|_code|Integer|필수|HTTP 상태 코드 3자리|
|_message|String|필수|응답 메시지|
|totalPages|Integer|필수|총 페이지 수|
|pageNumber|Integer|필수|현재 Pagintation Index|
|numberOfElements|Integer|필수|현재 페이지의 옷장 개수|
|size|Integer|필수|한 페이지의 최대 옷장 개수|
|totalElements|Long|필수|총 옷장 수|
|isLast|Boolean|필수|마지막 페이지 여부|
|isFirst|Boolean|필수|첫 페이지 여부|
|contents|Object[]|필수|옷장 목록|

#### contents 상세 정보 
|파라미터|타입|필수 여부|설명|
|------|---|---|---|
|id|Long|필수|옷장 id|
|name|String|필수|옷장 이름|
|clothesCnt|Integer|필수|옷장에 걸려있는 옷 개수|
|likeCnt|Integer|필수|좋아요 개수|
|memberName|String|필수|옷장 주인 이름|

### 사용자 옷장 상세 정보 가져오기
```
/api/v1/member/wardrobe 
```

|메서드|요청 URL|
|------|---|
|GET|api/v1/member/wardrobe|

#### Response Parameters
|파라미터|타입|필수 여부|설명|비고|
|------|---|---|---|---|
|_code|Integer|필수|HTTP 상태 코드 3자리||
|_message|String|필수|응답 메시지||
|id|Long|필수|옷장 id||
|likeCnt|Integer|필수|좋아요 수||
|memberName|String|필수|주인 이름||
|email|String|필수|주인 이메일||
|name|String|필수|옷장 이름||
|isPublic|String|필수|공개 여부|"Y" OR "N"|
|imageServerPath|String|필수|이미지 Path||



