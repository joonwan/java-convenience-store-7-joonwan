# java-convenience-store-precourse

---

## 목차

[1. 기능 구현 목록](#기능-구현-목록)

## 기능 구현 목록

--- 

### ✅ 프로모션 파일 읽고 초기화 하기

- [x] 프로모션 파일 에서 프로모션 목록을 읽어온다.
    - [x] 해당 경로 또는 파일이 없을 경우 예외를 발생 시킨다.
- [x] 한 줄씩 읽으며 `name` `buy` `get` `start_date` `end_date` 로 분리해 프로모션 객체로 만든다.
    - [ ] 이름이 비어있을 경우 예외를 발생시킨다.
    - [x] `buy` 와 `get` 이 정수형이 아닐 경우 예외를 발생 시킨다.
    - [x] `start_date` 와 `end_date` 가 형식이 올바르지 않을 경우 예외를 발생 시킨다.
- [x] 생성된 객체들을 임시 프로모션 데이터 베이스에 저장한다.

### ✅ 상품 파일 읽고 초기화 하기

- [x] 상품 파일 에서 상품 목록을 읽어온다.
    - [x] 해당 경로 또는 파일이 없을 경우 예외를 발생 시킨다.
- [x] 한 줄씩 읽으며 `name` `price` `quantity` `promotion` 로 분리해 상품 객체로 만든다.
    - [x] 이름이 비어있을 경우 예외를 발생시킨다.
    - [x] `price` 와 `quantity` 이 정수형이 아닐 경우 예외를 발생 시킨다.
    - [x] 입력 받은 `promotion` 이 임시 프로모션 데이터 베이스에 존재하지 않을 경우 예외를 발생시킨다.
- [x] 생성된 객체들을 임시 상품 데이터 베이스에 저장한다.

### ✅ 환영 인사 출력하기

- [x]  `안녕하세요. W편의점입니다.` 메시지를 출력한다.

### ✅ 보유 상품 목록 출력하기

- [x] `현재 보유하고 있는 상품입니다.` 를 출력한다.
- [x] 개행을 출력한다.
- [x] `- {상품명} {가격} {재고} {프로모션 이름}` 을 각 상품별로 출력한다.
    - [x] 만약 재고가 0개라면 `재고 없음` 을 출력한다.
    - [x] 프로모션이 적용가능한 상품일 경우 프로모션 재고 정보를 출력 후 일반 재고 정보를 출력한다.

### ✅ 구매할 상품명과 수량 입력 받기

- [x] 개행을 출력한다.
- [x] `구매하실 상품명과 수량을 입력해 주세요. (예: [사이다-2],[감자칩-1])` 를 출력한다.
- [x] 입력 형식이 맞는지 확인한다.
    - [x] 빈 문자열이 들어올 경우 예외를 발생 시킨다.
    - [x] 제품명과 수량이 `[ ]` 사이에 들어있지 않은 경우 예외를 발생시킨다.
    - [x] 제품명과 수량이 `-` 로 구분되어 있지 않을 경우 예외를 발생 시킨다.
    - [x] 상품이 빈 문자열이 아닐 경우 예외를 발생 시킨다.
    - [x] 수량이 숫자가 아닐 경우 예외를 발생 시킨다.
- [x] 예외가 발생할 경우 `[ERROR]` 로 시작하는 오류 메시지와 함께 상황에 맞는 안내문구를 출력한다. 이후 재입력을 받는다.

### ✅ 주문 상품 생성하기

- [x] 상품이 존재하는지 확인한다.
    - [x] 상품이 존재하지 않을 경우 예외를 발생시킨다.
- [ ] 주문 수량이 정상적인지 확인한다.
    - [x] 수량이 양수가 아닐 경우 예외를 발생 시킨다.
    - [x] 주문수량이 숫자가 아닐 경우 예외를 발생시킨다.
    - [x] 주문 수량이 양수가 아닐 경우 예외를 발생시킨다.
    - [x] 주문 수량이 재고 수를 초과할 경우 예외를 발생 시킨다.
- [x] 예외가 발생할 경우 `[ERROR]` 로 시작하는 오류 메시지와 함께 상황에 맞는 안내문구를 출력한다. 이후 재입력을 받는다.

### ✅ 주문 생성하기

- [x] 주문 상품을 담은 주문을 생성한다.

### ✅ 주문 상품별 프로모션 적용 가능 여부 확인후 타입 설정하기

- [x] 프로모션 적용 여부를 확인한다.
- [x] 프로모션이 존재하지 않거나 해당 날짜가 아닌 경우 `DEFAULT` 로 타입을 설정한다.

- [x] 주문 상품의 주문 수량이 프로모션 재고 내에서 해결 가능한지 확인한다.
    - [x] 주문 수량이 프로모션 재고보다 많을 경우 주문 상품의 타입을 `PARTIAL_PROMOTION` 로 설정한다.

- [x] 추가 증정이 가능한지 확인한다.
    - [x] `주문 수량 % (프로모션의 buy + 프로모션의 get)` 이 프로모션의 `buy` 랑 동일 한지 확인한다.
    - [x] `프로모션 재고 수량 - 주문 수량` 이 프로모션의 `get` 보다 크거나 같은지 확인한다.
    - [x] 만약 위 두 조건을 모두 만족할 경우 주문 상품의 타입을 `ADDITIONAL_PROMOTION` 로 설정한다.
    - [x] 아닐 경우 `DEFAULT` 로 설정한다.

### ✅ 타입별로 추가 증정가능 수량, 일부 상품 프로모션 적용 불가 수량 계산하기

- [x] 주문 상품의 타입이 `DEFAULT` 일 경우 추가 증정 가능 수량, 프로모션 적용 불가 상품 수량을 0으로 한다.
- [x] 주문 상품의 타입이 `ADDITIONAL_PROMOTION` 일 경우 해당 프로모션의 `get` 값을 추가증정 가능 수량으로 설정한다.
- [x] 주문 상품의 타입이 `PARTIAL_PROMOTION` 일 경우 프로모션이 적용되지 않는 일부 수량을 계산하여 적용 불가 수량으로 설정한다.
    - [x] 프로모션이 적용되지 않는 일부 수량 : `주문수량 - (프로모션 재고수량 / (buy + get)) * (buy + get)`

### ✅ 주문에 프로모션 적용하기

- [ ] 각 상품의 프로모션 존재 여부와 프로모션 기간을 고려하여 프로모션 적용 여부를 확인한다.
- [ ] 프로모션 적용이 가능할 경우 `현재 {상품명}은(는) {프로모션에 의한 증정 개수}개를 무료로 더 받을 수 있습니다. 추가하시겠습니까? (Y/N)` 메시지를 출력한다.
- [ ] 프로모션 재고가 부족하지만 일반 재고에서 충당이 가능할 경우 아래 메시지를 출력한다.
    - [ ] `현재 {상품명} {수량}개는 프로모션 할인이 적용되지 않습니다. 그래도 구매하시겠습니까? (Y/N)`
- [ ] `Y` 또는 `N` 이외의 값이 들어올 경우 `[ERROR]` 로 시작하는 오류 메시지와 함께 상황에 맞는 안내를 출력한다. 이후 재입력을 받는다.

### ✅멤버십 할인 적용하기

- [ ] 멤버십 할인 적용 여부를 확인하기 위해 아래 안내문구를 출력한다.
    - `멤버십 할인을 받으시겠습니까? (Y/N)`
- [ ] `Y` 를 입력 받을경우 멤버십 할인을 적용한다.
    - [ ] 멤버십 회원은 프로모션 미적용 금액의 30% 를 할인 받는다.
    - [ ] 프로모션 적용 후 남은 금액에 대해 멤버십 할인을 적용한다.
    - [ ] 멤버십 할인의 최대 한도는 8,000 원 이다.
- [ ] `N` 를 입력 받을 경우 멤버십 할인을 적용하지 않는다.
- [ ] 이외의 값이 들어올 경우 `[ERROR]` 로 시작하는 오류 메시지와 함께 상황에 맞는 안내를 출력한다. 이후 재입력을 받는다.

### ✅영수증 출력하기

- [ ] `===========W 편의점=============` 를 출력한다.
- [ ] 주문한 상품별로 `{상품명} {수량} {금액}` 를 출력한다.
- [ ] `===========증    정=============` 를 출력한다.
- [ ] 프로모션에 의해 무료로 제공된 상품의 목록을 출력한다.
- [ ] `==============================` 를 출력한다.
- [ ] 금액 정보를 출력한다.
    - [ ] `{총 구매액} {총 구입 수량} {총 금액}` 을 출력한다.
    - [ ] 프로모션 할인에 의해 적용된 할인 금액을 출력한다.
    - [ ] 멤버십 할인에 의해 적용된 할인 금액을 출력한다.
    - [ ] 할인이 적용된 총 금액을 출력한다.
- [ ] 영수증의 구성요소를 보기 좋게 정렬하여 고객이 쉽게 금액과 수량을 확인할 수 있게 해야 한다.

### ✅ 추가 구매 여부를 입력받기

- [ ] `감사합니다. 구매하고 싶은 다른 상품이 있나요? (Y/N)` 를 출력한다.
- [ ] `Y` 를 입력받을 경우 다시 [환영인사 출력하기](#-환영-인사-출력하기) 로 이동한다.
- [ ] `N` 을 입력받은 경우 프로그램을 종료한다.
- [ ] `Y` 그리고 `N` 이외의 값을 입력 받을 경우 `[ERROR]` 로 시작하는 오류 메시지와 함께 상황에 맞는 안내문구를 출력해야 한다. 이후 재입력을 받는다.
