## 옷장 관련 API 
### 옷장 추가하기
```
/api/v1/wardrobes
```

|메서드|요청 URL|
|------|---|
|POST|/api/v1/wardrobes|

#### 설명
> 해당 API는 application/json이 아닌 formData로 넘겨주셔야 합니다.

#### 예시
```javascript
var formData = new FormData();
formData.append('file', 파일);
formData.append('data', requestBody);
```

#### Request Parameters
|파라미터|타입|필수 여부|설명|비고|
|------|---|---|---|---|
|name|String|필수|내용|formData-data|
|isPublic|Boolean|필수|옷장 공개 여부|formData-data|
|file|MultipartFile|필수|옷장 이미지|formData-file|

#### Response Parameters
|파라미터|타입|필수 여부|설명|
|------|---|---|---|
|_code|Integer|필수|HTTP 상태 코드 3자리|
|_message|String|필수|응답 메시지|

### 옷장 리스트 가져오기 
```
/api/v1/wardrobes?page_number=1&page_size=10 
```

|메서드|요청 URL|
|------|---|
|GET|api/v1/wardrobes|

#### Request Parameters 
|파라미터|타입|필수 여부|설명|
|------|---|---|---|
|page_number|Integer|필수|Pagination Index로 페이지 시작 번호는 `1`로 한다.|Query Parameter|
|page_size|Integer|필수|한 페이지에 포함되는 최대 Element 수를 의미한다.|Query Parameter|


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

### 옷장 안에 들어가 있는 Cloth 리스트 가져오기
```
/api/v1/wardrobes/{id}/clothes?page_number=1&page_size=10 
```

|메서드|요청 URL|
|------|---|
|GET|/api/v1/wardrobes/{id}/clothes|

#### Request Parameters
|파라미터|타입|필수 여부|설명|비고|
|------|---|---|---|---|
|id|Long|필수|옷장 고유 id|PathVariable|
|page_number|Integer|필수|Pagination Index로 페이지 시작 번호는 `1`로 한다.|Query Parameter|
|page_size|Integer|필수|한 페이지에 포함되는 최대 Element 수를 의미한다.|Query Parameter|


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
|contents|Object[]|필수|Cloth 목록|

#### contents 상세 정보
|파라미터|타입|필수 여부|설명|비고|
|------|---|---|---|---|
|id|Long|필수|Cloth id|
|clothName|String|필수|Cloth 이름|
|clothType|String|필수|Cloth 종류|
|buyingDate|String|필수|Cloth 구매 일자|YYYY-MM-DD|
|clothBrand|String|필수|브랜드|
|imageServerPath|String|필수|이미지 Path|

### 옷장 안에 Cloth 추가하기
```
/api/v1/wardrobes/{id}/cloth
```

|메서드|요청 URL|
|------|---|
|POST|/api/v1/wardrobes/{id}/cloth|

#### 설명 
> 해당 API는 application/json이 아닌 formData로 넘겨주셔야 합니다. 

#### 예시
```javascript
var formData = new FormData();
formData.append('file', 파일);
formData.append('data', requestBody);
```

#### Request Parameters
|파라미터|타입|필수 여부|설명|비고|
|------|---|---|---|---|
|id|Long|필수|옷장 고유 id|PathVariable|
|clothName|Long|필수|Cloth 이름|RequestBody|
|clothType|Long|필수|Cloth 종류|RequestBody|
|buyingDate|Long|필수|구매 일자|RequestBody|
|buyingWay|Long|필수|구매 방법|RequestBody|
|clothColor|Long|필수|Cloth 색상|RequestBody|
|clothBrand|Long|필수|Cloth 브랜드|RequestBody|
|file|MultipartFile|필수|Cloth 이미지|RequestBody|

#### Response Parameters
|파라미터|타입|필수 여부|설명|
|------|---|---|---|
|_code|Integer|필수|HTTP 상태 코드 3자리|
|_message|String|필수|응답 메시지|

### 옷장 안에 Cloth 제거하기
```
/api/v1/wardrobes/{wardrobe_id}/cloth/{cloth_id}
```

|메서드|요청 URL|
|------|---|
|DELETE|/api/v1/wardrobes/{id}/cloth|

#### Request Parameters
|파라미터|타입|필수 여부|설명|비고|
|------|---|---|---|---|
|wardrobe_id|Long|필수|옷장 고유 id|
|cloth_id|Long|필수|Cloth 고유 id|

#### Response Parameters
|파라미터|타입|필수 여부|설명|
|------|---|---|---|
|_code|Integer|필수|HTTP 상태 코드 3자리|
|_message|String|필수|응답 메시지|

<br/>

## Cloth 관련 API
### Cloth 리스트 가져오기
```
/api/v1/clothes?page=1&pageSize=10 
```

|메서드|요청 URL|
|------|---|
|GET|api/v1/clothes|

#### Request Parameters
|파라미터|타입|필수 여부|설명|
|------|---|---|---|
|page|Integer|필수|Pagination Index로 페이지 시작 번호는 `1`로 한다.|
|pageSize|Integer|필수|한 페이지에 포함되는 최대 Element 수를 의미한다.|


#### Response Parameters
|파라미터|타입|필수 여부|설명|
|------|---|---|---|
|_code|Integer|필수|HTTP 상태 코드 3자리|
|_message|String|필수|응답 메시지|
|totalPages|Integer|필수|총 페이지 수|
|pageNumber|Integer|필수|현재 Pagintation Index|
|numberOfElements|Integer|필수|현재 페이지의 Cloth 개수|
|size|Integer|필수|한 페이지의 최대 Cloth 개수|
|totalElements|Long|필수|총 Cloth 수|
|isLast|Boolean|필수|마지막 페이지 여부|
|isFirst|Boolean|필수|첫 페이지 여부|
|contents|Object[]|필수|Cloth 목록|

#### contents 상세 정보
|파라미터|타입|필수 여부|설명|비고|
|------|---|---|---|---|
|id|Long|필수|Cloth id|
|clothName|String|필수|Cloth 이름|
|clothType|String|필수|Cloth 종류|
|buyingDate|String|필수|Cloth 구매 일자|YYYY-MM-DD|
|clothBrand|String|필수|브랜드|
|imageServerPath|String|필수|이미지 Path|

### Cloth 상세 정보 가져오기
```
/api/v1/clothes/{id}
```

|메서드|요청 URL|
|------|---|
|GET|api/v1/clothes/{id}|

#### Request Parameters 
|파라미터|타입|필수 여부|설명|
|------|---|---|---|
|id|Long|필수|Cloth id|

#### Response Parameters
|파라미터|타입|필수 여부|설명|비고|
|------|---|---|---|---|
|_code|Integer|필수|HTTP 상태 코드 3자리||
|_message|String|필수|응답 메시지||
|id|Long|필수|Cloth id||
|likeCnt|Integer|필수|좋아요 수||
|memberName|String|필수|주인 이름||
|email|String|필수|주인 이메일||
|clothName|String|필수|Cloth 이름||
|clothType|String|필수|Cloth 종류||
|buyingDate|String|필수|Cloth 구매 일자|YYYY-MM-DD|
|buyingWay|String|필수|Cloth 구매 방법||
|clothColor|String|필수|Cloth 색상||
|clothBrand|String|필수|Cloth 브랜드||
|imageServerPath|String|필수|이미지 Path||

<br/>

## Record 관련 API
### 코디 리스트 가져오기
```
/api/v1/records?page=1&pageSize=10 
```

|메서드|요청 URL|
|------|---|
|GET|api/v1/records|

#### Request Parameters
|파라미터|타입|필수 여부|설명|
|------|---|---|---|
|page|Integer|필수|Pagination Index로 페이지 시작 번호는 `1`로 한다.|
|pageSize|Integer|필수|한 페이지에 포함되는 최대 Element 수를 의미한다.|


#### Response Parameters
|파라미터|타입|필수 여부|설명|
|------|---|---|---|
|_code|Integer|필수|HTTP 상태 코드 3자리|
|_message|String|필수|응답 메시지|
|totalPages|Integer|필수|총 페이지 수|
|pageNumber|Integer|필수|현재 Pagintation Index|
|numberOfElements|Integer|필수|현재 페이지의 Cloth 개수|
|size|Integer|필수|한 페이지의 최대 Cloth 개수|
|totalElements|Long|필수|총 Cloth 수|
|isLast|Boolean|필수|마지막 페이지 여부|
|isFirst|Boolean|필수|첫 페이지 여부|
|contents|Object[]|필수|Record 목록|

#### contents 상세 정보
|파라미터|타입|필수 여부|설명|비고|
|------|---|---|---|---|
|id|Long|필수|Record id|
|clothId|String|필수|Cloth id|
|clothName|String|필수|Cloth 이름|
|content|String|필수|내용|
|likeCnt|Integer|필수|좋아요 수|
|imageServerPath|String|필수|Cloth 구매 일자|YYYY-MM-DD|
|memberName|String|필수|작성자 이름|
|email|String|필수|작성자 이메일|
|createdDate|LocalDateTime|필수|작성 날짜|
|ModifiedDate|LocalDateTime|필수|수정 날짜|

### 코디 상세 정보 가져오기
```
/api/v1/clothes/{id}
```

|메서드|요청 URL|
|------|---|
|GET|api/v1/clothes/{id}|

#### Request Parameters
|파라미터|타입|필수 여부|설명|
|------|---|---|---|
|id|Long|필수|Cloth id|

#### Response Parameters
|파라미터|타입|필수 여부|설명|비고|
|------|---|---|---|---|
|_code|Integer|필수|HTTP 상태 코드 3자리|
|_message|String|필수|응답 메시지|
|id|Long|필수|Record id|
|clothId|String|필수|Cloth id|
|clothName|String|필수|Cloth 이름|
|content|String|필수|내용|
|likeCnt|Integer|필수|좋아요 수|
|imageServerPath|String|필수|Cloth 구매 일자|YYYY-MM-DD|
|createdDate|LocalDateTime|필수|작성 날짜|
|ModifiedDate|LocalDateTime|필수|수정 날짜|

### 특정 Cloth와 관련된 코디 리스트 가져오기
```
/api/v1/clothes/{id}/records?page_number=1&page_size=10 
```

|메서드|요청 URL|
|------|---|
|GET|/api/v1/clothes/{id}/records|

#### Request Parameters
|파라미터|타입|필수 여부|설명|비고|
|------|---|---|---|---|
|id|Long|필수|Cloth 고유 id|PathVariable|
|page_number|Integer|필수|Pagination Index로 페이지 시작 번호는 `1`로 한다.|Query Parameter|
|page_size|Integer|필수|한 페이지에 포함되는 최대 Element 수를 의미한다.|Query Parameter|


#### Response Parameters
|파라미터|타입|필수 여부|설명|
|------|---|---|---|
|_code|Integer|필수|HTTP 상태 코드 3자리|
|_message|String|필수|응답 메시지|
|totalPages|Integer|필수|총 페이지 수|
|pageNumber|Integer|필수|현재 Pagintation Index|
|numberOfElements|Integer|필수|현재 페이지의 Cloth 개수|
|size|Integer|필수|한 페이지의 최대 Cloth 개수|
|totalElements|Long|필수|총 Cloth 수|
|isLast|Boolean|필수|마지막 페이지 여부|
|isFirst|Boolean|필수|첫 페이지 여부|
|contents|Object[]|필수|코디 목록|

#### contents 상세 정보
|파라미터|타입|필수 여부|설명|비고|
|------|---|---|---|---|
|id|Long|필수|Record id|
|clothId|String|필수|Cloth id|
|clothName|String|필수|Cloth 이름|
|content|String|필수|내용|
|likeCnt|Integer|필수|좋아요 수|
|imageServerPath|String|필수|Cloth 구매 일자|YYYY-MM-DD|
|memberName|String|필수|작성자 이름|
|email|String|필수|작성자 이메일|
|createdDate|LocalDateTime|필수|작성 날짜|
|ModifiedDate|LocalDateTime|필수|수정 날짜|

### Cloth 관련 코디 추가하기
```
/api/v1/clothes/{id}/records
```

|메서드|요청 URL|
|------|---|
|POST|/api/v1/clothes/{id}/record|

#### 설명
> 해당 API는 application/json이 아닌 formData로 넘겨주셔야 합니다.

#### 예시
```javascript
var formData = new FormData();
formData.append('file', 파일);
formData.append('data', requestBody);
```

#### Request Parameters
|파라미터|타입|필수 여부|설명|비고|
|------|---|---|---|---|
|id|Long|필수|Cloth 고유 id|PathVariable|
|content|String|필수|내용|formData-data|
|file|MultipartFile|필수|코디 관련 이미지|formData-file|

#### Response Parameters
|파라미터|타입|필수 여부|설명|
|------|---|---|---|
|_code|Integer|필수|HTTP 상태 코드 3자리|
|_message|String|필수|응답 메시지|

### Cloth 관련 코디 제거하기
```
/api/v1/clothes/{clothId}/records/{recordId}
```

|메서드|요청 URL|
|------|---|
|DELETE|/api/v1/clothes/{cloth_id}/records/{record_id}|

#### Request Parameters
|파라미터|타입|필수 여부|설명|비고|
|------|---|---|---|---|
|cloth_id|Long|필수|Cloth 고유 id|PathVariable|
|record_id|필수|코디 고유 id|PathVariable|

#### Response Parameters
|파라미터|타입|필수 여부|설명|
|------|---|---|---|
|_code|Integer|필수|HTTP 상태 코드 3자리|
|_message|String|필수|응답 메시지|


<br/>

## Post 관련 API (옷장, Cloth, Record 공통 API)
### 좋아요 여부 확인하기
```
/api/v1/posts/{id}/{member_id}/is_like
```

|메서드|요청 URL|
|------|---|
|GET|api/v1/posts/{id}/{member_id}/is_like|

#### Request Parameters
|파라미터|타입|필수 여부|설명|
|------|---|---|---|
|id|Long|필수|Post 고유 id|
|member_id|Long|필수|유저 고유 id|

#### Response Parameters
|파라미터|타입|필수 여부|설명|비고|
|------|---|---|---|---|
|_code|Integer|필수|HTTP 상태 코드 3자리|
|_message|String|필수|응답 메시지|
|isLike|Boolean|필수|좋아요 여부|
|clickedDate|LocalDateTime|필수|좋아요 클릭 시간|

### 좋아요 변경하기
```
/api/v1/posts/{id}/like
```

|메서드|요청 URL|
|------|---|
|PUT|api/v1/posts/{id}/like|

#### 설명
> 만약 좋아요가 눌린 상태면 취소하고, 눌리지 않은 상태면 좋아요를 추가합니다.

#### Request Parameters
|파라미터|타입|필수 여부|설명|
|------|---|---|---|
|id|Long|필수|Post 고유 id|

#### Response Parameters
|파라미터|타입|필수 여부|설명|비고|
|------|---|---|---|---|
|_code|Integer|필수|HTTP 상태 코드 3자리|
|_message|String|필수|응답 메시지|

### 댓글 목록 가져오기
```
/api/v1/posts/{id}/comments
```

|메서드|요청 URL|
|------|---|
|GET|/api/v1/posts/{id}/comments|

#### Request Parameters
|파라미터|타입|필수 여부|설명|
|------|---|---|---|
|id|Long|필수|Post 고유 id|

#### Response Parameters
|파라미터|타입|필수 여부|설명|
|------|---|---|---|
|_code|Integer|필수|HTTP 상태 코드 3자리|
|_message|String|필수|응답 메시지|
|totalPages|Integer|필수|총 페이지 수|
|pageNumber|Integer|필수|현재 Pagintation Index|
|numberOfElements|Integer|필수|현재 페이지의 Cloth 개수|
|size|Integer|필수|한 페이지의 최대 Cloth 개수|
|totalElements|Long|필수|총 Cloth 수|
|isLast|Boolean|필수|마지막 페이지 여부|
|isFirst|Boolean|필수|첫 페이지 여부|
|contents|Object[]|필수|댓글 목록|

#### contents 상세 정보
|파라미터|타입|필수 여부|설명|비고|
|------|---|---|---|---|
|id|Long|필수|댓글 고유 id|
|content|String|필수|내용|
|memberName|String|필수|작성자 이름|
|email|String|필수|작성자 이메일|
|createdDate|LocalDateTime|필수|작성 날짜|
|ModifiedDate|LocalDateTime|필수|수정 날짜|

### 댓글 추가하기
```
/api/v1/posts/{id}/comment
```

|메서드|요청 URL|
|------|---|
|POST|api/v1/posts/{id}/comment|

#### Request Parameters
|파라미터|타입|필수 여부|설명|비고|
|------|---|---|---|---|
|id|Long|필수|Post 고유 id|PathVariable|
|content|Long|필수|댓글 내용|RequestBody|

#### Response Parameters
|파라미터|타입|필수 여부|설명|비고|
|------|---|---|---|---|
|_code|Integer|필수|HTTP 상태 코드 3자리|
|_message|String|필수|응답 메시지|

### 댓글 삭제하기
```
/api/v1/posts/{post_id}comment/{comment_id}
```

|메서드|요청 URL|
|------|---|
|DELETE|/api/v1/posts/{post_id}comment/{comment_id}|

#### Request Parameters
|파라미터|타입|필수 여부|설명|
|------|---|---|---|
|post_id|Long|필수|Post 고유 id|
|comment_id|Long|필수|댓글 고유 id|

#### Response Parameters
|파라미터|타입|필수 여부|설명|비고|
|------|---|---|---|---|
|_code|Integer|필수|HTTP 상태 코드 3자리|
|_message|String|필수|응답 메시지|