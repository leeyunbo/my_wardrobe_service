## 유저 관련 API
### 유저 목록 가져오기
```
http://localhost:8080/api/v1/users?page=1&pageSize=10 
```

|메서드|요청 URL|
|------|---|
|GET|api/v1/users|

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
|isLast|Integer|필수|마지막 페이지 여부|
|isFirst|Integer|필수|첫 페이지 여부|
|contents|Object[]|필수|유저 목록|

#### contents 상세 정보
|파라미터|타입|필수 여부|설명|
|------|---|---|---|
|id|Long|필수|옷장 id|
|name|String|필수|유저 이름|
|email|String|필수|유저 이메일|


