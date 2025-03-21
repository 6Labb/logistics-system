SET search_path TO hubs;
INSERT INTO p_hub (id, hub_name, hub_address, latitude, longitude) VALUES
('11e98756-d7a2-f948-b1b1-0242ac120001', '서울특별시 센터', '서울특별시 송파구 송파대로 55', 37.4742027808565, 127.123621185562),
('11e98756-d7a2-f948-b1b1-0242ac120002', '경기 북부 센터', '경기도 고양시 덕양구 권율대로 570', 37.6403771056018, 126.87379545786),
('11e98756-d7a2-f948-b1b1-0242ac120003', '경기 남부 센터', '경기도 이천시 덕평로 257-21', 37.1896213142136, 127.375050006958),
('11e98756-d7a2-f948-b1b1-0242ac120004', '부산광역시 센터', '부산 동구 중앙대로 206', 35.117605126596, 129.045060216345),
('11e98756-d7a2-f948-b1b1-0242ac120005', '대구광역시 센터', '대구 북구 태평로 161', 35.8758849492106, 128.596129208483),
('11e98756-d7a2-f948-b1b1-0242ac120006', '인천광역시 센터', '인천 남동구 정각로 29', 37.4560499608337, 126.705255744089),
('11e98756-d7a2-f948-b1b1-0242ac120007', '광주광역시 센터', '광주 서구 내방로 111', 35.1600994105234, 126.851461925213),
('11e98756-d7a2-f948-b1b1-0242ac120008', '대전광역시 센터', '대전 서구 둔산로 100', 36.3505, 127.3891),
('11e98756-d7a2-f948-b1b1-0242ac120009', '울산광역시 센터', '울산 남구 중앙로 201', 35.5390270962011, 129.311356392207),
('11e98756-d7a2-f948-b1b1-0242ac120010', '세종특별자치시 센터', '세종특별자치시 한누리대로 2130', 36.4800579897497, 127.289039408864),
('11e98756-d7a2-f948-b1b1-0242ac120011', '강원특별자치도 센터', '강원특별자치도 춘천시 중앙로 1', 37.8800729197963, 127.727907820318),
('11e98756-d7a2-f948-b1b1-0242ac120012', '충청북도 센터', '충북 청주시 상당구 상당로 82', 36.6353867908159, 127.491428436987),
('11e98756-d7a2-f948-b1b1-0242ac120013', '충청남도 센터', '충남 홍성군 홍북읍 충남대로 21', 36.6590416999343, 126.673057036952),
('11e98756-d7a2-f948-b1b1-0242ac120014', '전북특별자치도 센터', '전북특별자치도 전주시 완산구 효자로 225', 35.8194621650578, 127.106396942356),
('11e98756-d7a2-f948-b1b1-0242ac120015', '전라남도 센터', '전남 무안군 삼향읍 오룡길 1', 34.8174727676363, 126.465415935304),
('11e98756-d7a2-f948-b1b1-0242ac120016', '경상북도 센터', '경북 안동시 풍천면 도청대로 455', 36.5761205474728, 128.505722686385),
('11e98756-d7a2-f948-b1b1-0242ac120017', '경상남도 센터', '경남 창원시 의창구 중앙대로 300', 35.2378032514675, 128.691940442146);

SET search_path TO companies;
INSERT INTO p_company (id, name, address, type, hub_id) VALUES
-- 공급업체들 (SUPPLIER)
('11e9-8756-d7a2-f948-b1b1-0242ac121001', '한국수산물유통(주)', '서울특별시 강남구 테헤란로 123', 'SUPPLIER', '11e98756-d7a2-f948-b1b1-0242ac120001'),
('11e9-8756-d7a2-f948-b1b1-0242ac121002', '바다마을영어조합법인', '부산광역시 서구 충무대로 256', 'SUPPLIER', '11e98756-d7a2-f948-b1b1-0242ac120004'),
('11e9-8756-d7a2-f948-b1b1-0242ac121003', '동해수산(주)', '강원특별자치도 강릉시 해안로 789', 'SUPPLIER', '11e98756-d7a2-f948-b1b1-0242ac120011'),
('11e9-8756-d7a2-f948-b1b1-0242ac121004', '남해어민협동조합', '경상남도 통영시 중앙로 345', 'SUPPLIER', '11e98756-d7a2-f948-b1b1-0242ac120017'),
('11e9-8756-d7a2-f948-b1b1-0242ac121005', '제주해양산업(주)', '제주특별자치도 제주시 연동로 567', 'SUPPLIER', '11e98756-d7a2-f948-b1b1-0242ac120001'),

-- 수령업체들 (RECEIVER)
('11e9-8756-d7a2-f948-b1b1-0242ac121011', '광동제약(주)', '서울특별시 서초구 바우뫼로 27', 'RECEIVER', '11e98756-d7a2-f948-b1b1-0242ac120001'),
('11e9-8756-d7a2-f948-b1b1-0242ac121012', '삼성전자(주)', '경기도 수원시 영통구 삼성로 129', 'RECEIVER', '11e98756-d7a2-f948-b1b1-0242ac120003'),
('11e9-8756-d7a2-f948-b1b1-0242ac121013', 'LG전자(주)', '서울특별시 영등포구 여의대로 128', 'RECEIVER', '11e98756-d7a2-f948-b1b1-0242ac120001'),
('11e9-8756-d7a2-f948-b1b1-0242ac121014', '현대자동차(주)', '서울특별시 서초구 헌릉로 12', 'RECEIVER', '11e98756-d7a2-f948-b1b1-0242ac120001'),
('11e9-8756-d7a2-f948-b1b1-0242ac121015', '기아자동차(주)', '서울특별시 서초구 강남대로 303', 'RECEIVER', '11e98756-d7a2-f948-b1b1-0242ac120001');

SET search_path TO products;
INSERT INTO p_product (id, name, quantity, hub_id, company_id) VALUES
-- 한국수산물유통(주) 제품 (SUPPLIER)
('11e9-8756-d7a2-f948-b1b1-0242ac131001', '고등어', 500, '11e98756-d7a2-f948-b1b1-0242ac120001', '11e9-8756-d7a2-f948-b1b1-0242ac121001'),
('11e9-8756-d7a2-f948-b1b1-0242ac131002', '연어', 300, '11e98756-d7a2-f948-b1b1-0242ac120001', '11e9-8756-d7a2-f948-b1b1-0242ac121001'),
('11e9-8756-d7a2-f948-b1b1-0242ac131003', '참치', 200, '11e98756-d7a2-f948-b1b1-0242ac120001', '11e9-8756-d7a2-f948-b1b1-0242ac121001'),

-- 바다마을영어조합법인 제품 (SUPPLIER)
('11e9-8756-d7a2-f948-b1b1-0242ac131004', '갈치', 250, '11e98756-d7a2-f948-b1b1-0242ac120004', '11e9-8756-d7a2-f948-b1b1-0242ac121002'),
('11e9-8756-d7a2-f948-b1b1-0242ac131005', '멸치', 1000, '11e98756-d7a2-f948-b1b1-0242ac120004', '11e9-8756-d7a2-f948-b1b1-0242ac121002'),

-- 광동제약(주) 제품 (RECEIVER)
('11e9-8756-d7a2-f948-b1b1-0242ac131016', '비타500', 1000, '11e98756-d7a2-f948-b1b1-0242ac120001', '11e9-8756-d7a2-f948-b1b1-0242ac121011'),
('11e9-8756-d7a2-f948-b1b1-0242ac131017', '원더피', 800, '11e98756-d7a2-f948-b1b1-0242ac120001', '11e9-8756-d7a2-f948-b1b1-0242ac121011');

SET search_path TO hubs;
INSERT INTO p_hub_manager (id, user_id, hub_id) VALUES
-- 서울특별시 센터
('11e9-8756-d7a2-f948-b1b1-0242ac141001', 50001, '11e98756-d7a2-f948-b1b1-0242ac120001'),

-- 경기 북부 센터
('11e9-8756-d7a2-f948-b1b1-0242ac141002', 50002, '11e98756-d7a2-f948-b1b1-0242ac120002'),

-- 경기 남부 센터
('11e9-8756-d7a2-f948-b1b1-0242ac141003', 50003, '11e98756-d7a2-f948-b1b1-0242ac120003'),

-- 부산광역시 센터
('11e9-8756-d7a2-f948-b1b1-0242ac141004', 50004, '11e98756-d7a2-f948-b1b1-0242ac120004'),

-- 대구광역시 센터
('11e9-8756-d7a2-f948-b1b1-0242ac141005', 50005, '11e98756-d7a2-f948-b1b1-0242ac120005'),

-- 인천광역시 센터
('11e9-8756-d7a2-f948-b1b1-0242ac141006', 50006, '11e98756-d7a2-f948-b1b1-0242ac120006'),

-- 광주광역시 센터
('11e9-8756-d7a2-f948-b1b1-0242ac141007', 50007, '11e98756-d7a2-f948-b1b1-0242ac120007'),

-- 대전광역시 센터
('11e9-8756-d7a2-f948-b1b1-0242ac141008', 50008, '11e98756-d7a2-f948-b1b1-0242ac120008'),

-- 울산광역시 센터
('11e9-8756-d7a2-f948-b1b1-0242ac141009', 50009, '11e98756-d7a2-f948-b1b1-0242ac120009'),

-- 세종특별자치시 센터
('11e9-8756-d7a2-f948-b1b1-0242ac141010', 50010, '11e98756-d7a2-f948-b1b1-0242ac120010'),

-- 강원특별자치도 센터
('11e9-8756-d7a2-f948-b1b1-0242ac141011', 50011, '11e98756-d7a2-f948-b1b1-0242ac120011'),

-- 충청북도 센터
('11e9-8756-d7a2-f948-b1b1-0242ac141012', 50012, '11e98756-d7a2-f948-b1b1-0242ac120012'),

-- 충청남도 센터
('11e9-8756-d7a2-f948-b1b1-0242ac141013', 50013, '11e98756-d7a2-f948-b1b1-0242ac120013'),

-- 전북특별자치도 센터
('11e9-8756-d7a2-f948-b1b1-0242ac141014', 50014, '11e98756-d7a2-f948-b1b1-0242ac120014'),

-- 전라남도 센터
('11e9-8756-d7a2-f948-b1b1-0242ac141015', 50015, '11e98756-d7a2-f948-b1b1-0242ac120015'),

-- 경상북도 센터
('11e9-8756-d7a2-f948-b1b1-0242ac141016', 50016, '11e98756-d7a2-f948-b1b1-0242ac120016'),

-- 경상남도 센터
('11e9-8756-d7a2-f948-b1b1-0242ac141017', 50017, '11e98756-d7a2-f948-b1b1-0242ac120017');

SET search_path TO deliveries;
-- 배송 담당자 데이터 삽입
INSERT INTO p_delivery_agent (user_id, type, delivery_sequence, hub_id, slack_id) VALUES
-- 서울특별시 센터
-- 업체 배송 담당자 (COMPANY 타입)
(20001, 'COMPANY', 0, '11e98756-d7a2-f948-b1b1-0242ac120001', 'seoul_company_agent1'),
(20002, 'COMPANY', 1, '11e98756-d7a2-f948-b1b1-0242ac120001', 'seoul_company_agent2'),
(20003, 'COMPANY', 2, '11e98756-d7a2-f948-b1b1-0242ac120001', 'seoul_company_agent3'),
(20004, 'COMPANY', 3, '11e98756-d7a2-f948-b1b1-0242ac120001', 'seoul_company_agent4'),
(20005, 'COMPANY', 4, '11e98756-d7a2-f948-b1b1-0242ac120001', 'seoul_company_agent5'),
(20006, 'COMPANY', 5, '11e98756-d7a2-f948-b1b1-0242ac120001', 'seoul_company_agent6'),
(20007, 'COMPANY', 6, '11e98756-d7a2-f948-b1b1-0242ac120001', 'seoul_company_agent7'),
(20008, 'COMPANY', 7, '11e98756-d7a2-f948-b1b1-0242ac120001', 'seoul_company_agent8'),
(20009, 'COMPANY', 8, '11e98756-d7a2-f948-b1b1-0242ac120001', 'seoul_company_agent9'),
(20010, 'COMPANY', 9, '11e98756-d7a2-f948-b1b1-0242ac120001', 'seoul_company_agent10'),

-- 서울특별시 센터 허브 배송 담당자 (HUB 타입)
(10001, 'HUB', 0, '11e98756-d7a2-f948-b1b1-0242ac120001', 'seoul_hub_agent1'),
(10002, 'HUB', 1, '11e98756-d7a2-f948-b1b1-0242ac120001', 'seoul_hub_agent2'),
(10003, 'HUB', 2, '11e98756-d7a2-f948-b1b1-0242ac120001', 'seoul_hub_agent3'),
(10004, 'HUB', 3, '11e98756-d7a2-f948-b1b1-0242ac120001', 'seoul_hub_agent4'),
(10005, 'HUB', 4, '11e98756-d7a2-f948-b1b1-0242ac120001', 'seoul_hub_agent5'),
(10006, 'HUB', 5, '11e98756-d7a2-f948-b1b1-0242ac120001', 'seoul_hub_agent6'),
(10007, 'HUB', 6, '11e98756-d7a2-f948-b1b1-0242ac120001', 'seoul_hub_agent7'),
(10008, 'HUB', 7, '11e98756-d7a2-f948-b1b1-0242ac120001', 'seoul_hub_agent8'),
(10009, 'HUB', 8, '11e98756-d7a2-f948-b1b1-0242ac120001', 'seoul_hub_agent9'),
(10010, 'HUB', 9, '11e98756-d7a2-f948-b1b1-0242ac120001', 'seoul_hub_agent10'),

-- 경기 북부 센터
-- 업체 배송 담당자 (COMPANY 타입)
(20011, 'COMPANY', 0, '11e98756-d7a2-f948-b1b1-0242ac120002', 'gyeonggi_north_company_agent1'),
(20012, 'COMPANY', 1, '11e98756-d7a2-f948-b1b1-0242ac120002', 'gyeonggi_north_company_agent2'),
(20013, 'COMPANY', 2, '11e98756-d7a2-f948-b1b1-0242ac120002', 'gyeonggi_north_company_agent3'),
(20014, 'COMPANY', 3, '11e98756-d7a2-f948-b1b1-0242ac120002', 'gyeonggi_north_company_agent4'),
(20015, 'COMPANY', 4, '11e98756-d7a2-f948-b1b1-0242ac120002', 'gyeonggi_north_company_agent5'),
(20016, 'COMPANY', 5, '11e98756-d7a2-f948-b1b1-0242ac120002', 'gyeonggi_north_company_agent6'),
(20017, 'COMPANY', 6, '11e98756-d7a2-f948-b1b1-0242ac120002', 'gyeonggi_north_company_agent7'),
(20018, 'COMPANY', 7, '11e98756-d7a2-f948-b1b1-0242ac120002', 'gyeonggi_north_company_agent8'),
(20019, 'COMPANY', 8, '11e98756-d7a2-f948-b1b1-0242ac120002', 'gyeonggi_north_company_agent9'),
(20020, 'COMPANY', 9, '11e98756-d7a2-f948-b1b1-0242ac120002', 'gyeonggi_north_company_agent10'),

-- 경기 북부 센터 허브 배송 담당자 (HUB 타입)
(10011, 'HUB', 0, '11e98756-d7a2-f948-b1b1-0242ac120002', 'gyeonggi_north_hub_agent1'),
(10012, 'HUB', 1, '11e98756-d7a2-f948-b1b1-0242ac120002', 'gyeonggi_north_hub_agent2'),
(10013, 'HUB', 2, '11e98756-d7a2-f948-b1b1-0242ac120002', 'gyeonggi_north_hub_agent3'),
(10014, 'HUB', 3, '11e98756-d7a2-f948-b1b1-0242ac120002', 'gyeonggi_north_hub_agent4'),
(10015, 'HUB', 4, '11e98756-d7a2-f948-b1b1-0242ac120002', 'gyeonggi_north_hub_agent5'),
(10016, 'HUB', 5, '11e98756-d7a2-f948-b1b1-0242ac120002', 'gyeonggi_north_hub_agent6'),
(10017, 'HUB', 6, '11e98756-d7a2-f948-b1b1-0242ac120002', 'gyeonggi_north_hub_agent7'),
(10018, 'HUB', 7, '11e98756-d7a2-f948-b1b1-0242ac120002', 'gyeonggi_north_hub_agent8'),
(10019, 'HUB', 8, '11e98756-d7a2-f948-b1b1-0242ac120002', 'gyeonggi_north_hub_agent9'),
(10020, 'HUB', 9, '11e98756-d7a2-f948-b1b1-0242ac120002', 'gyeonggi_north_hub_agent10'),

-- 경기 남부 센터
-- 업체 배송 담당자 (COMPANY 타입)
(20021, 'COMPANY', 0, '11e98756-d7a2-f948-b1b1-0242ac120003', 'gyeonggi_south_company_agent1'),
(20022, 'COMPANY', 1, '11e98756-d7a2-f948-b1b1-0242ac120003', 'gyeonggi_south_company_agent2'),
(20023, 'COMPANY', 2, '11e98756-d7a2-f948-b1b1-0242ac120003', 'gyeonggi_south_company_agent3'),
(20024, 'COMPANY', 3, '11e98756-d7a2-f948-b1b1-0242ac120003', 'gyeonggi_south_company_agent4'),
(20025, 'COMPANY', 4, '11e98756-d7a2-f948-b1b1-0242ac120003', 'gyeonggi_south_company_agent5'),
(20026, 'COMPANY', 5, '11e98756-d7a2-f948-b1b1-0242ac120003', 'gyeonggi_south_company_agent6'),
(20027, 'COMPANY', 6, '11e98756-d7a2-f948-b1b1-0242ac120003', 'gyeonggi_south_company_agent7'),
(20028, 'COMPANY', 7, '11e98756-d7a2-f948-b1b1-0242ac120003', 'gyeonggi_south_company_agent8'),
(20029, 'COMPANY', 8, '11e98756-d7a2-f948-b1b1-0242ac120003', 'gyeonggi_south_company_agent9'),
(20030, 'COMPANY', 9, '11e98756-d7a2-f948-b1b1-0242ac120003', 'gyeonggi_south_company_agent10'),

-- 경기 남부 센터 허브 배송 담당자 (HUB 타입)
(10021, 'HUB', 0, '11e98756-d7a2-f948-b1b1-0242ac120003', 'gyeonggi_south_hub_agent1'),
(10022, 'HUB', 1, '11e98756-d7a2-f948-b1b1-0242ac120003', 'gyeonggi_south_hub_agent2'),
(10023, 'HUB', 2, '11e98756-d7a2-f948-b1b1-0242ac120003', 'gyeonggi_south_hub_agent3'),
(10024, 'HUB', 3, '11e98756-d7a2-f948-b1b1-0242ac120003', 'gyeonggi_south_hub_agent4'),
(10025, 'HUB', 4, '11e98756-d7a2-f948-b1b1-0242ac120003', 'gyeonggi_south_hub_agent5'),
(10026, 'HUB', 5, '11e98756-d7a2-f948-b1b1-0242ac120003', 'gyeonggi_south_hub_agent6'),
(10027, 'HUB', 6, '11e98756-d7a2-f948-b1b1-0242ac120003', 'gyeonggi_south_hub_agent7'),
(10028, 'HUB', 7, '11e98756-d7a2-f948-b1b1-0242ac120003', 'gyeonggi_south_hub_agent8'),
(10029, 'HUB', 8, '11e98756-d7a2-f948-b1b1-0242ac120003', 'gyeonggi_south_hub_agent9'),
(10030, 'HUB', 9, '11e98756-d7a2-f948-b1b1-0242ac120003', 'gyeonggi_south_hub_agent10'),

-- 부산광역시 센터
-- 업체 배송 담당자 (COMPANY 타입)
(20031, 'COMPANY', 0, '11e98756-d7a2-f948-b1b1-0242ac120004', 'busan_company_agent1'),
(20032, 'COMPANY', 1, '11e98756-d7a2-f948-b1b1-0242ac120004', 'busan_company_agent2'),
(20033, 'COMPANY', 2, '11e98756-d7a2-f948-b1b1-0242ac120004', 'busan_company_agent3'),
(20034, 'COMPANY', 3, '11e98756-d7a2-f948-b1b1-0242ac120004', 'busan_company_agent4'),
(20035, 'COMPANY', 4, '11e98756-d7a2-f948-b1b1-0242ac120004', 'busan_company_agent5'),
(20036, 'COMPANY', 5, '11e98756-d7a2-f948-b1b1-0242ac120004', 'busan_company_agent6'),
(20037, 'COMPANY', 6, '11e98756-d7a2-f948-b1b1-0242ac120004', 'busan_company_agent7'),
(20038, 'COMPANY', 7, '11e98756-d7a2-f948-b1b1-0242ac120004', 'busan_company_agent8'),
(20039, 'COMPANY', 8, '11e98756-d7a2-f948-b1b1-0242ac120004', 'busan_company_agent9'),
(20040, 'COMPANY', 9, '11e98756-d7a2-f948-b1b1-0242ac120004', 'busan_company_agent10'),

-- 부산광역시 센터 허브 배송 담당자 (HUB 타입)
(10031, 'HUB', 0, '11e98756-d7a2-f948-b1b1-0242ac120004', 'busan_hub_agent1'),
(10032, 'HUB', 1, '11e98756-d7a2-f948-b1b1-0242ac120004', 'busan_hub_agent2'),
(10033, 'HUB', 2, '11e98756-d7a2-f948-b1b1-0242ac120004', 'busan_hub_agent3'),
(10034, 'HUB', 3, '11e98756-d7a2-f948-b1b1-0242ac120004', 'busan_hub_agent4'),
(10035, 'HUB', 4, '11e98756-d7a2-f948-b1b1-0242ac120004', 'busan_hub_agent5'),
(10036, 'HUB', 5, '11e98756-d7a2-f948-b1b1-0242ac120004', 'busan_hub_agent6'),
(10037, 'HUB', 6, '11e98756-d7a2-f948-b1b1-0242ac120004', 'busan_hub_agent7'),
(10038, 'HUB', 7, '11e98756-d7a2-f948-b1b1-0242ac120004', 'busan_hub_agent8'),
(10039, 'HUB', 8, '11e98756-d7a2-f948-b1b1-0242ac120004', 'busan_hub_agent9'),
(10040, 'HUB', 9, '11e98756-d7a2-f948-b1b1-0242ac120004', 'busan_hub_agent10'),

-- 대구광역시 센터
-- 업체 배송 담당자 (COMPANY 타입)
(20041, 'COMPANY', 0, '11e98756-d7a2-f948-b1b1-0242ac120005', 'daegu_company_agent1'),
(20042, 'COMPANY', 1, '11e98756-d7a2-f948-b1b1-0242ac120005', 'daegu_company_agent2'),
(20043, 'COMPANY', 2, '11e98756-d7a2-f948-b1b1-0242ac120005', 'daegu_company_agent3'),
(20044, 'COMPANY', 3, '11e98756-d7a2-f948-b1b1-0242ac120005', 'daegu_company_agent4'),
(20045, 'COMPANY', 4, '11e98756-d7a2-f948-b1b1-0242ac120005', 'daegu_company_agent5'),
(20046, 'COMPANY', 5, '11e98756-d7a2-f948-b1b1-0242ac120005', 'daegu_company_agent6'),
(20047, 'COMPANY', 6, '11e98756-d7a2-f948-b1b1-0242ac120005', 'daegu_company_agent7'),
(20048, 'COMPANY', 7, '11e98756-d7a2-f948-b1b1-0242ac120005', 'daegu_company_agent8'),
(20049, 'COMPANY', 8, '11e98756-d7a2-f948-b1b1-0242ac120005', 'daegu_company_agent9'),
(20050, 'COMPANY', 9, '11e98756-d7a2-f948-b1b1-0242ac120005', 'daegu_company_agent10'),

-- 대구광역시 센터 허브 배송 담당자 (HUB 타입)
(10041, 'HUB', 0, '11e98756-d7a2-f948-b1b1-0242ac120005', 'daegu_hub_agent1'),
(10042, 'HUB', 1, '11e98756-d7a2-f948-b1b1-0242ac120005', 'daegu_hub_agent2'),
(10043, 'HUB', 2, '11e98756-d7a2-f948-b1b1-0242ac120005', 'daegu_hub_agent3'),
(10044, 'HUB', 3, '11e98756-d7a2-f948-b1b1-0242ac120005', 'daegu_hub_agent4'),
(10045, 'HUB', 4, '11e98756-d7a2-f948-b1b1-0242ac120005', 'daegu_hub_agent5'),
(10046, 'HUB', 5, '11e98756-d7a2-f948-b1b1-0242ac120005', 'daegu_hub_agent6'),
(10047, 'HUB', 6, '11e98756-d7a2-f948-b1b1-0242ac120005', 'daegu_hub_agent7'),
(10048, 'HUB', 7, '11e98756-d7a2-f948-b1b1-0242ac120005', 'daegu_hub_agent8'),
(10049, 'HUB', 8, '11e98756-d7a2-f948-b1b1-0242ac120005', 'daegu_hub_agent9'),
(10050, 'HUB', 9, '11e98756-d7a2-f948-b1b1-0242ac120005', 'daegu_hub_agent10'),

-- 인천광역시 센터
-- 업체 배송 담당자 (COMPANY 타입)
(20051, 'COMPANY', 0, '11e98756-d7a2-f948-b1b1-0242ac120006', 'incheon_company_agent1'),
(20052, 'COMPANY', 1, '11e98756-d7a2-f948-b1b1-0242ac120006', 'incheon_company_agent2'),
(20053, 'COMPANY', 2, '11e98756-d7a2-f948-b1b1-0242ac120006', 'incheon_company_agent3'),
(20054, 'COMPANY', 3, '11e98756-d7a2-f948-b1b1-0242ac120006', 'incheon_company_agent4'),
(20055, 'COMPANY', 4, '11e98756-d7a2-f948-b1b1-0242ac120006', 'incheon_company_agent5'),
(20056, 'COMPANY', 5, '11e98756-d7a2-f948-b1b1-0242ac120006', 'incheon_company_agent6'),
(20057, 'COMPANY', 6, '11e98756-d7a2-f948-b1b1-0242ac120006', 'incheon_company_agent7'),
(20058, 'COMPANY', 7, '11e98756-d7a2-f948-b1b1-0242ac120006', 'incheon_company_agent8'),
(20059, 'COMPANY', 8, '11e98756-d7a2-f948-b1b1-0242ac120006', 'incheon_company_agent9'),
(20060, 'COMPANY', 9, '11e98756-d7a2-f948-b1b1-0242ac120006', 'incheon_company_agent10'),

-- 인천광역시 센터 허브 배송 담당자 (HUB 타입)
(10051, 'HUB', 0, '11e98756-d7a2-f948-b1b1-0242ac120006', 'incheon_hub_agent1'),
(10052, 'HUB', 1, '11e98756-d7a2-f948-b1b1-0242ac120006', 'incheon_hub_agent2'),
(10053, 'HUB', 2, '11e98756-d7a2-f948-b1b1-0242ac120006', 'incheon_hub_agent3'),
(10054, 'HUB', 3, '11e98756-d7a2-f948-b1b1-0242ac120006', 'incheon_hub_agent4'),
(10055, 'HUB', 4, '11e98756-d7a2-f948-b1b1-0242ac120006', 'incheon_hub_agent5'),
(10056, 'HUB', 5, '11e98756-d7a2-f948-b1b1-0242ac120006', 'incheon_hub_agent6'),
(10057, 'HUB', 6, '11e98756-d7a2-f948-b1b1-0242ac120006', 'incheon_hub_agent7'),
(10058, 'HUB', 7, '11e98756-d7a2-f948-b1b1-0242ac120006', 'incheon_hub_agent8'),
(10059, 'HUB', 8, '11e98756-d7a2-f948-b1b1-0242ac120006', 'incheon_hub_agent9'),
(10060, 'HUB', 9, '11e98756-d7a2-f948-b1b1-0242ac120006', 'incheon_hub_agent10'),

-- 광주광역시 센터
-- 업체 배송 담당자 (COMPANY 타입)
(20061, 'COMPANY', 0, '11e98756-d7a2-f948-b1b1-0242ac120007', 'gwangju_company_agent1'),
(20062, 'COMPANY', 1, '11e98756-d7a2-f948-b1b1-0242ac120007', 'gwangju_company_agent2'),
(20063, 'COMPANY', 2, '11e98756-d7a2-f948-b1b1-0242ac120007', 'gwangju_company_agent3'),
(20064, 'COMPANY', 3, '11e98756-d7a2-f948-b1b1-0242ac120007', 'gwangju_company_agent4'),
(20065, 'COMPANY', 4, '11e98756-d7a2-f948-b1b1-0242ac120007', 'gwangju_company_agent5'),
(20066, 'COMPANY', 5, '11e98756-d7a2-f948-b1b1-0242ac120007', 'gwangju_company_agent6'),
(20067, 'COMPANY', 6, '11e98756-d7a2-f948-b1b1-0242ac120007', 'gwangju_company_agent7'),
(20068, 'COMPANY', 7, '11e98756-d7a2-f948-b1b1-0242ac120007', 'gwangju_company_agent8'),
(20069, 'COMPANY', 8, '11e98756-d7a2-f948-b1b1-0242ac120007', 'gwangju_company_agent9'),
(20070, 'COMPANY', 9, '11e98756-d7a2-f948-b1b1-0242ac120007', 'gwangju_company_agent10'),

-- 광주광역시 센터 허브 배송 담당자 (HUB 타입)
(10061, 'HUB', 0, '11e98756-d7a2-f948-b1b1-0242ac120007', 'gwangju_hub_agent1'),
(10062, 'HUB', 1, '11e98756-d7a2-f948-b1b1-0242ac120007', 'gwangju_hub_agent2'),
(10063, 'HUB', 2, '11e98756-d7a2-f948-b1b1-0242ac120007', 'gwangju_hub_agent3'),
(10064, 'HUB', 3, '11e98756-d7a2-f948-b1b1-0242ac120007', 'gwangju_hub_agent4'),
(10065, 'HUB', 4, '11e98756-d7a2-f948-b1b1-0242ac120007', 'gwangju_hub_agent5'),
(10066, 'HUB', 5, '11e98756-d7a2-f948-b1b1-0242ac120007', 'gwangju_hub_agent6'),
(10067, 'HUB', 6, '11e98756-d7a2-f948-b1b1-0242ac120007', 'gwangju_hub_agent7'),
(10068, 'HUB', 7, '11e98756-d7a2-f948-b1b1-0242ac120007', 'gwangju_hub_agent8'),
(10069, 'HUB', 8, '11e98756-d7a2-f948-b1b1-0242ac120007', 'gwangju_hub_agent9'),
(10070, 'HUB', 9, '11e98756-d7a2-f948-b1b1-0242ac120007', 'gwangju_hub_agent10'),

-- 대전광역시 센터
-- 업체 배송 담당자 (COMPANY 타입)
(20071, 'COMPANY', 0, '11e98756-d7a2-f948-b1b1-0242ac120008', 'daejeon_company_agent1'),
(20072, 'COMPANY', 1, '11e98756-d7a2-f948-b1b1-0242ac120008', 'daejeon_company_agent2'),
(20073, 'COMPANY', 2, '11e98756-d7a2-f948-b1b1-0242ac120008', 'daejeon_company_agent3'),
(20074, 'COMPANY', 3, '11e98756-d7a2-f948-b1b1-0242ac120008', 'daejeon_company_agent4'),
(20075, 'COMPANY', 4, '11e98756-d7a2-f948-b1b1-0242ac120008', 'daejeon_company_agent5'),
(20076, 'COMPANY', 5, '11e98756-d7a2-f948-b1b1-0242ac120008', 'daejeon_company_agent6'),
(20077, 'COMPANY', 6, '11e98756-d7a2-f948-b1b1-0242ac120008', 'daejeon_company_agent7'),
(20078, 'COMPANY', 7, '11e98756-d7a2-f948-b1b1-0242ac120008', 'daejeon_company_agent8'),
(20079, 'COMPANY', 8, '11e98756-d7a2-f948-b1b1-0242ac120008', 'daejeon_company_agent9'),
(20080, 'COMPANY', 9, '11e98756-d7a2-f948-b1b1-0242ac120008', 'daejeon_company_agent10'),

-- 대전광역시 센터 허브 배송 담당자 (HUB 타입)
(10071, 'HUB', 0, '11e98756-d7a2-f948-b1b1-0242ac120008', 'daejeon_hub_agent1'),
(10072, 'HUB', 1, '11e98756-d7a2-f948-b1b1-0242ac120008', 'daejeon_hub_agent2'),
(10073, 'HUB', 2, '11e98756-d7a2-f948-b1b1-0242ac120008', 'daejeon_hub_agent3'),
(10074, 'HUB', 3, '11e98756-d7a2-f948-b1b1-0242ac120008', 'daejeon_hub_agent4'),
(10075, 'HUB', 4, '11e98756-d7a2-f948-b1b1-0242ac120008', 'daejeon_hub_agent5'),
(10076, 'HUB', 5, '11e98756-d7a2-f948-b1b1-0242ac120008', 'daejeon_hub_agent6'),
(10077, 'HUB', 6, '11e98756-d7a2-f948-b1b1-0242ac120008', 'daejeon_hub_agent7'),
(10078, 'HUB', 7, '11e98756-d7a2-f948-b1b1-0242ac120008', 'daejeon_hub_agent8'),
(10079, 'HUB', 8, '11e98756-d7a2-f948-b1b1-0242ac120008', 'daejeon_hub_agent9'),
(10080, 'HUB', 9, '11e98756-d7a2-f948-b1b1-0242ac120008', 'daejeon_hub_agent10'),

-- 울산광역시 센터
-- 업체 배송 담당자 (COMPANY 타입)
(20081, 'COMPANY', 0, '11e98756-d7a2-f948-b1b1-0242ac120009', 'ulsan_company_agent1'),
(20082, 'COMPANY', 1, '11e98756-d7a2-f948-b1b1-0242ac120009', 'ulsan_company_agent2'),
(20083, 'COMPANY', 2, '11e98756-d7a2-f948-b1b1-0242ac120009', 'ulsan_company_agent3'),
(20084, 'COMPANY', 3, '11e98756-d7a2-f948-b1b1-0242ac120009', 'ulsan_company_agent4'),
(20085, 'COMPANY', 4, '11e98756-d7a2-f948-b1b1-0242ac120009', 'ulsan_company_agent5'),
(20086, 'COMPANY', 5, '11e98756-d7a2-f948-b1b1-0242ac120009', 'ulsan_company_agent6'),
(20087, 'COMPANY', 6, '11e98756-d7a2-f948-b1b1-0242ac120009', 'ulsan_company_agent7'),
(20088, 'COMPANY', 7, '11e98756-d7a2-f948-b1b1-0242ac120009', 'ulsan_company_agent8'),
(20089, 'COMPANY', 8, '11e98756-d7a2-f948-b1b1-0242ac120009', 'ulsan_company_agent9'),
(20090, 'COMPANY', 9, '11e98756-d7a2-f948-b1b1-0242ac120009', 'ulsan_company_agent10'),

-- 울산광역시 센터 허브 배송 담당자 (HUB 타입)
(10081, 'HUB', 0, '11e98756-d7a2-f948-b1b1-0242ac120009', 'ulsan_hub_agent1'),
(10082, 'HUB', 1, '11e98756-d7a2-f948-b1b1-0242ac120009', 'ulsan_hub_agent2'),
(10083, 'HUB', 2, '11e98756-d7a2-f948-b1b1-0242ac120009', 'ulsan_hub_agent3'),
(10084, 'HUB', 3, '11e98756-d7a2-f948-b1b1-0242ac120009', 'ulsan_hub_agent4'),
(10085, 'HUB', 4, '11e98756-d7a2-f948-b1b1-0242ac120009', 'ulsan_hub_agent5'),
(10086, 'HUB', 5, '11e98756-d7a2-f948-b1b1-0242ac120009', 'ulsan_hub_agent6'),
(10087, 'HUB', 6, '11e98756-d7a2-f948-b1b1-0242ac120009', 'ulsan_hub_agent7'),
(10088, 'HUB', 7, '11e98756-d7a2-f948-b1b1-0242ac120009', 'ulsan_hub_agent8'),
(10089, 'HUB', 8, '11e98756-d7a2-f948-b1b1-0242ac120009', 'ulsan_hub_agent9'),
(10090, 'HUB', 9, '11e98756-d7a2-f948-b1b1-0242ac120009', 'ulsan_hub_agent10'),

-- 세종특별자치시 센터
-- 업체 배송 담당자 (COMPANY 타입)
(20091, 'COMPANY', 0, '11e98756-d7a2-f948-b1b1-0242ac120010', 'sejong_company_agent1'),
(20092, 'COMPANY', 1, '11e98756-d7a2-f948-b1b1-0242ac120010', 'sejong_company_agent2'),
(20093, 'COMPANY', 2, '11e98756-d7a2-f948-b1b1-0242ac120010', 'sejong_company_agent3'),
(20094, 'COMPANY', 3, '11e98756-d7a2-f948-b1b1-0242ac120010', 'sejong_company_agent4'),
(20095, 'COMPANY', 4, '11e98756-d7a2-f948-b1b1-0242ac120010', 'sejong_company_agent5'),
(20096, 'COMPANY', 5, '11e98756-d7a2-f948-b1b1-0242ac120010', 'sejong_company_agent6'),
(20097, 'COMPANY', 6, '11e98756-d7a2-f948-b1b1-0242ac120010', 'sejong_company_agent7'),
(20098, 'COMPANY', 7, '11e98756-d7a2-f948-b1b1-0242ac120010', 'sejong_company_agent8'),
(20099, 'COMPANY', 8, '11e98756-d7a2-f948-b1b1-0242ac120010', 'sejong_company_agent9'),
(20100, 'COMPANY', 9, '11e98756-d7a2-f948-b1b1-0242ac120010', 'sejong_company_agent10'),

-- 세종특별자치시 센터 허브 배송 담당자 (HUB 타입)
(10091, 'HUB', 0, '11e98756-d7a2-f948-b1b1-0242ac120010', 'sejong_hub_agent1'),
(10092, 'HUB', 1, '11e98756-d7a2-f948-b1b1-0242ac120010', 'sejong_hub_agent2'),
(10093, 'HUB', 2, '11e98756-d7a2-f948-b1b1-0242ac120010', 'sejong_hub_agent3'),
(10094, 'HUB', 3, '11e98756-d7a2-f948-b1b1-0242ac120010', 'sejong_hub_agent4'),
(10095, 'HUB', 4, '11e98756-d7a2-f948-b1b1-0242ac120010', 'sejong_hub_agent5'),
(10096, 'HUB', 5, '11e98756-d7a2-f948-b1b1-0242ac120010', 'sejong_hub_agent6'),
(10097, 'HUB', 6, '11e98756-d7a2-f948-b1b1-0242ac120010', 'sejong_hub_agent7'),
(10098, 'HUB', 7, '11e98756-d7a2-f948-b1b1-0242ac120010', 'sejong_hub_agent8'),
(10099, 'HUB', 8, '11e98756-d7a2-f948-b1b1-0242ac120010', 'sejong_hub_agent9'),
(10100, 'HUB', 9, '11e98756-d7a2-f948-b1b1-0242ac120010', 'sejong_hub_agent10'),

-- 강원특별자치도 센터
-- 업체 배송 담당자 (COMPANY 타입)
(20101, 'COMPANY', 0, '11e98756-d7a2-f948-b1b1-0242ac120011', 'gangwon_company_agent1'),
(20102, 'COMPANY', 1, '11e98756-d7a2-f948-b1b1-0242ac120011', 'gangwon_company_agent2'),
(20103, 'COMPANY', 2, '11e98756-d7a2-f948-b1b1-0242ac120011', 'gangwon_company_agent3'),
(20104, 'COMPANY', 3, '11e98756-d7a2-f948-b1b1-0242ac120011', 'gangwon_company_agent4'),
(20105, 'COMPANY', 4, '11e98756-d7a2-f948-b1b1-0242ac120011', 'gangwon_company_agent5'),
(20106, 'COMPANY', 5, '11e98756-d7a2-f948-b1b1-0242ac120011', 'gangwon_company_agent6'),
(20107, 'COMPANY', 6, '11e98756-d7a2-f948-b1b1-0242ac120011', 'gangwon_company_agent7'),
(20108, 'COMPANY', 7, '11e98756-d7a2-f948-b1b1-0242ac120011', 'gangwon_company_agent8'),
(20109, 'COMPANY', 8, '11e98756-d7a2-f948-b1b1-0242ac120011', 'gangwon_company_agent9'),
(20110, 'COMPANY', 9, '11e98756-d7a2-f948-b1b1-0242ac120011', 'gangwon_company_agent10'),

-- 강원특별자치도 센터 허브 배송 담당자 (HUB 타입)
(10101, 'HUB', 0, '11e98756-d7a2-f948-b1b1-0242ac120011', 'gangwon_hub_agent1'),
(10102, 'HUB', 1, '11e98756-d7a2-f948-b1b1-0242ac120011', 'gangwon_hub_agent2'),
(10103, 'HUB', 2, '11e98756-d7a2-f948-b1b1-0242ac120011', 'gangwon_hub_agent3'),
(10104, 'HUB', 3, '11e98756-d7a2-f948-b1b1-0242ac120011', 'gangwon_hub_agent4'),
(10105, 'HUB', 4, '11e98756-d7a2-f948-b1b1-0242ac120011', 'gangwon_hub_agent5'),
(10106, 'HUB', 5, '11e98756-d7a2-f948-b1b1-0242ac120011', 'gangwon_hub_agent6'),
(10107, 'HUB', 6, '11e98756-d7a2-f948-b1b1-0242ac120011', 'gangwon_hub_agent7'),
(10108, 'HUB', 7, '11e98756-d7a2-f948-b1b1-0242ac120011', 'gangwon_hub_agent8'),
(10109, 'HUB', 8, '11e98756-d7a2-f948-b1b1-0242ac120011', 'gangwon_hub_agent9'),
(10110, 'HUB', 9, '11e98756-d7a2-f948-b1b1-0242ac120011', 'gangwon_hub_agent10'),

-- 충청북도 센터
-- 업체 배송 담당자 (COMPANY 타입)
(20111, 'COMPANY', 0, '11e98756-d7a2-f948-b1b1-0242ac120012', 'chungbuk_company_agent1'),
(20112, 'COMPANY', 1, '11e98756-d7a2-f948-b1b1-0242ac120012', 'chungbuk_company_agent2'),
(20113, 'COMPANY', 2, '11e98756-d7a2-f948-b1b1-0242ac120012', 'chungbuk_company_agent3'),
(20114, 'COMPANY', 3, '11e98756-d7a2-f948-b1b1-0242ac120012', 'chungbuk_company_agent4'),
(20115, 'COMPANY', 4, '11e98756-d7a2-f948-b1b1-0242ac120012', 'chungbuk_company_agent5'),
(20116, 'COMPANY', 5, '11e98756-d7a2-f948-b1b1-0242ac120012', 'chungbuk_company_agent6'),
(20117, 'COMPANY', 6, '11e98756-d7a2-f948-b1b1-0242ac120012', 'chungbuk_company_agent7'),
(20118, 'COMPANY', 7, '11e98756-d7a2-f948-b1b1-0242ac120012', 'chungbuk_company_agent8'),
(20119, 'COMPANY', 8, '11e98756-d7a2-f948-b1b1-0242ac120012', 'chungbuk_company_agent9'),
(20120, 'COMPANY', 9, '11e98756-d7a2-f948-b1b1-0242ac120012', 'chungbuk_company_agent10'),

-- 충청북도 센터 허브 배송 담당자 (HUB 타입)
(10111, 'HUB', 0, '11e98756-d7a2-f948-b1b1-0242ac120012', 'chungbuk_hub_agent1'),
(10112, 'HUB', 1, '11e98756-d7a2-f948-b1b1-0242ac120012', 'chungbuk_hub_agent2'),
(10113, 'HUB', 2, '11e98756-d7a2-f948-b1b1-0242ac120012', 'chungbuk_hub_agent3'),
(10114, 'HUB', 3, '11e98756-d7a2-f948-b1b1-0242ac120012', 'chungbuk_hub_agent4'),
(10115, 'HUB', 4, '11e98756-d7a2-f948-b1b1-0242ac120012', 'chungbuk_hub_agent5'),
(10116, 'HUB', 5, '11e98756-d7a2-f948-b1b1-0242ac120012', 'chungbuk_hub_agent6'),
(10117, 'HUB', 6, '11e98756-d7a2-f948-b1b1-0242ac120012', 'chungbuk_hub_agent7'),
(10118, 'HUB', 7, '11e98756-d7a2-f948-b1b1-0242ac120012', 'chungbuk_hub_agent8'),
(10119, 'HUB', 8, '11e98756-d7a2-f948-b1b1-0242ac120012', 'chungbuk_hub_agent9'),
(10120, 'HUB', 9, '11e98756-d7a2-f948-b1b1-0242ac120012', 'chungbuk_hub_agent10'),

-- 충청남도 센터
-- 업체 배송 담당자 (COMPANY 타입)
(20121, 'COMPANY', 0, '11e98756-d7a2-f948-b1b1-0242ac120013', 'chungnam_company_agent1'),
(20122, 'COMPANY', 1, '11e98756-d7a2-f948-b1b1-0242ac120013', 'chungnam_company_agent2'),
(20123, 'COMPANY', 2, '11e98756-d7a2-f948-b1b1-0242ac120013', 'chungnam_company_agent3'),
(20124, 'COMPANY', 3, '11e98756-d7a2-f948-b1b1-0242ac120013', 'chungnam_company_agent4'),
(20125, 'COMPANY', 4, '11e98756-d7a2-f948-b1b1-0242ac120013', 'chungnam_company_agent5'),
(20126, 'COMPANY', 5, '11e98756-d7a2-f948-b1b1-0242ac120013', 'chungnam_company_agent6'),
(20127, 'COMPANY', 6, '11e98756-d7a2-f948-b1b1-0242ac120013', 'chungnam_company_agent7'),
(20128, 'COMPANY', 7, '11e98756-d7a2-f948-b1b1-0242ac120013', 'chungnam_company_agent8'),
(20129, 'COMPANY', 8, '11e98756-d7a2-f948-b1b1-0242ac120013', 'chungnam_company_agent9'),
(20130, 'COMPANY', 9, '11e98756-d7a2-f948-b1b1-0242ac120013', 'chungnam_company_agent10'),

-- 충청남도 센터 허브 배송 담당자 (HUB 타입)
(10121, 'HUB', 0, '11e98756-d7a2-f948-b1b1-0242ac120013', 'chungnam_hub_agent1'),
(10122, 'HUB', 1, '11e98756-d7a2-f948-b1b1-0242ac120013', 'chungnam_hub_agent2'),
(10123, 'HUB', 2, '11e98756-d7a2-f948-b1b1-0242ac120013', 'chungnam_hub_agent3'),
(10124, 'HUB', 3, '11e98756-d7a2-f948-b1b1-0242ac120013', 'chungnam_hub_agent4'),
(10125, 'HUB', 4, '11e98756-d7a2-f948-b1b1-0242ac120013', 'chungnam_hub_agent5'),
(10126, 'HUB', 5, '11e98756-d7a2-f948-b1b1-0242ac120013', 'chungnam_hub_agent6'),
(10127, 'HUB', 6, '11e98756-d7a2-f948-b1b1-0242ac120013', 'chungnam_hub_agent7'),
(10128, 'HUB', 7, '11e98756-d7a2-f948-b1b1-0242ac120013', 'chungnam_hub_agent8'),
(10129, 'HUB', 8, '11e98756-d7a2-f948-b1b1-0242ac120013', 'chungnam_hub_agent9'),
(10130, 'HUB', 9, '11e98756-d7a2-f948-b1b1-0242ac120013', 'chungnam_hub_agent10'),

-- 전북특별자치도 센터
-- 업체 배송 담당자 (COMPANY 타입)
(20131, 'COMPANY', 0, '11e98756-d7a2-f948-b1b1-0242ac120014', 'jeonbuk_company_agent1'),
(20132, 'COMPANY', 1, '11e98756-d7a2-f948-b1b1-0242ac120014', 'jeonbuk_company_agent2'),
(20133, 'COMPANY', 2, '11e98756-d7a2-f948-b1b1-0242ac120014', 'jeonbuk_company_agent3'),
(20134, 'COMPANY', 3, '11e98756-d7a2-f948-b1b1-0242ac120014', 'jeonbuk_company_agent4'),
(20135, 'COMPANY', 4, '11e98756-d7a2-f948-b1b1-0242ac120014', 'jeonbuk_company_agent5'),
(20136, 'COMPANY', 5, '11e98756-d7a2-f948-b1b1-0242ac120014', 'jeonbuk_company_agent6'),
(20137, 'COMPANY', 6, '11e98756-d7a2-f948-b1b1-0242ac120014', 'jeonbuk_company_agent7'),
(20138, 'COMPANY', 7, '11e98756-d7a2-f948-b1b1-0242ac120014', 'jeonbuk_company_agent8'),
(20139, 'COMPANY', 8, '11e98756-d7a2-f948-b1b1-0242ac120014', 'jeonbuk_company_agent9'),
(20140, 'COMPANY', 9, '11e98756-d7a2-f948-b1b1-0242ac120014', 'jeonbuk_company_agent10'),

-- 전북특별자치도 센터 허브 배송 담당자 (HUB 타입)
(10131, 'HUB', 0, '11e98756-d7a2-f948-b1b1-0242ac120014', 'jeonbuk_hub_agent1'),
(10132, 'HUB', 1, '11e98756-d7a2-f948-b1b1-0242ac120014', 'jeonbuk_hub_agent2'),
(10133, 'HUB', 2, '11e98756-d7a2-f948-b1b1-0242ac120014', 'jeonbuk_hub_agent3'),
(10134, 'HUB', 3, '11e98756-d7a2-f948-b1b1-0242ac120014', 'jeonbuk_hub_agent4'),
(10135, 'HUB', 4, '11e98756-d7a2-f948-b1b1-0242ac120014', 'jeonbuk_hub_agent5'),
(10136, 'HUB', 5, '11e98756-d7a2-f948-b1b1-0242ac120014', 'jeonbuk_hub_agent6'),
(10137, 'HUB', 6, '11e98756-d7a2-f948-b1b1-0242ac120014', 'jeonbuk_hub_agent7'),
(10138, 'HUB', 7, '11e98756-d7a2-f948-b1b1-0242ac120014', 'jeonbuk_hub_agent8'),
(10139, 'HUB', 8, '11e98756-d7a2-f948-b1b1-0242ac120014', 'jeonbuk_hub_agent9'),
(10140, 'HUB', 9, '11e98756-d7a2-f948-b1b1-0242ac120014', 'jeonbuk_hub_agent10'),

-- 전라남도 센터
-- 업체 배송 담당자 (COMPANY 타입)
(20141, 'COMPANY', 0, '11e98756-d7a2-f948-b1b1-0242ac120015', 'jeonnam_company_agent1'),
(20142, 'COMPANY', 1, '11e98756-d7a2-f948-b1b1-0242ac120015', 'jeonnam_company_agent2'),
(20143, 'COMPANY', 2, '11e98756-d7a2-f948-b1b1-0242ac120015', 'jeonnam_company_agent3'),
(20144, 'COMPANY', 3, '11e98756-d7a2-f948-b1b1-0242ac120015', 'jeonnam_company_agent4'),
(20145, 'COMPANY', 4, '11e98756-d7a2-f948-b1b1-0242ac120015', 'jeonnam_company_agent5'),
(20146, 'COMPANY', 5, '11e98756-d7a2-f948-b1b1-0242ac120015', 'jeonnam_company_agent6'),
(20147, 'COMPANY', 6, '11e98756-d7a2-f948-b1b1-0242ac120015', 'jeonnam_company_agent7'),
(20148, 'COMPANY', 7, '11e98756-d7a2-f948-b1b1-0242ac120015', 'jeonnam_company_agent8'),
(20149, 'COMPANY', 8, '11e98756-d7a2-f948-b1b1-0242ac120015', 'jeonnam_company_agent9'),
(20150, 'COMPANY', 9, '11e98756-d7a2-f948-b1b1-0242ac120015', 'jeonnam_company_agent10'),

-- 전라남도 센터 허브 배송 담당자 (HUB 타입)
(10141, 'HUB', 0, '11e98756-d7a2-f948-b1b1-0242ac120015', 'jeonnam_hub_agent1'),
(10142, 'HUB', 1, '11e98756-d7a2-f948-b1b1-0242ac120015', 'jeonnam_hub_agent2'),
(10143, 'HUB', 2, '11e98756-d7a2-f948-b1b1-0242ac120015', 'jeonnam_hub_agent3'),
(10144, 'HUB', 3, '11e98756-d7a2-f948-b1b1-0242ac120015', 'jeonnam_hub_agent4'),
(10145, 'HUB', 4, '11e98756-d7a2-f948-b1b1-0242ac120015', 'jeonnam_hub_agent5'),
(10146, 'HUB', 5, '11e98756-d7a2-f948-b1b1-0242ac120015', 'jeonnam_hub_agent6'),
(10147, 'HUB', 6, '11e98756-d7a2-f948-b1b1-0242ac120015', 'jeonnam_hub_agent7'),
(10148, 'HUB', 7, '11e98756-d7a2-f948-b1b1-0242ac120015', 'jeonnam_hub_agent8'),
(10149, 'HUB', 8, '11e98756-d7a2-f948-b1b1-0242ac120015', 'jeonnam_hub_agent9'),
(10150, 'HUB', 9, '11e98756-d7a2-f948-b1b1-0242ac120015', 'jeonnam_hub_agent10'),

-- 경상북도 센터
-- 업체 배송 담당자 (COMPANY 타입)
(20151, 'COMPANY', 0, '11e98756-d7a2-f948-b1b1-0242ac120016', 'gyeongbuk_company_agent1'),
(20152, 'COMPANY', 1, '11e98756-d7a2-f948-b1b1-0242ac120016', 'gyeongbuk_company_agent2'),
(20153, 'COMPANY', 2, '11e98756-d7a2-f948-b1b1-0242ac120016', 'gyeongbuk_company_agent3'),
(20154, 'COMPANY', 3, '11e98756-d7a2-f948-b1b1-0242ac120016', 'gyeongbuk_company_agent4'),
(20155, 'COMPANY', 4, '11e98756-d7a2-f948-b1b1-0242ac120016', 'gyeongbuk_company_agent5'),
(20156, 'COMPANY', 5, '11e98756-d7a2-f948-b1b1-0242ac120016', 'gyeongbuk_company_agent6'),
(20157, 'COMPANY', 6, '11e98756-d7a2-f948-b1b1-0242ac120016', 'gyeongbuk_company_agent7'),
(20158, 'COMPANY', 7, '11e98756-d7a2-f948-b1b1-0242ac120016', 'gyeongbuk_company_agent8'),
(20159, 'COMPANY', 8, '11e98756-d7a2-f948-b1b1-0242ac120016', 'gyeongbuk_company_agent9'),
(20160, 'COMPANY', 9, '11e98756-d7a2-f948-b1b1-0242ac120016', 'gyeongbuk_company_agent10'),

-- 경상북도 센터 허브 배송 담당자 (HUB 타입)
(10151, 'HUB', 0, '11e98756-d7a2-f948-b1b1-0242ac120016', 'gyeongbuk_hub_agent1'),
(10152, 'HUB', 1, '11e98756-d7a2-f948-b1b1-0242ac120016', 'gyeongbuk_hub_agent2'),
(10153, 'HUB', 2, '11e98756-d7a2-f948-b1b1-0242ac120016', 'gyeongbuk_hub_agent3'),
(10154, 'HUB', 3, '11e98756-d7a2-f948-b1b1-0242ac120016', 'gyeongbuk_hub_agent4'),
(10155, 'HUB', 4, '11e98756-d7a2-f948-b1b1-0242ac120016', 'gyeongbuk_hub_agent5'),
(10156, 'HUB', 5, '11e98756-d7a2-f948-b1b1-0242ac120016', 'gyeongbuk_hub_agent6'),
(10157, 'HUB', 6, '11e98756-d7a2-f948-b1b1-0242ac120016', 'gyeongbuk_hub_agent7'),
(10158, 'HUB', 7, '11e98756-d7a2-f948-b1b1-0242ac120016', 'gyeongbuk_hub_agent8'),
(10159, 'HUB', 8, '11e98756-d7a2-f948-b1b1-0242ac120016', 'gyeongbuk_hub_agent9'),
(10160, 'HUB', 9, '11e98756-d7a2-f948-b1b1-0242ac120016', 'gyeongbuk_hub_agent10'),

-- 경상남도 센터
-- 업체 배송 담당자 (COMPANY 타입)
(20161, 'COMPANY', 0, '11e98756-d7a2-f948-b1b1-0242ac120017', 'gyeongnam_company_agent1'),
(20162, 'COMPANY', 1, '11e98756-d7a2-f948-b1b1-0242ac120017', 'gyeongnam_company_agent2'),
(20163, 'COMPANY', 2, '11e98756-d7a2-f948-b1b1-0242ac120017', 'gyeongnam_company_agent3'),
(20164, 'COMPANY', 3, '11e98756-d7a2-f948-b1b1-0242ac120017', 'gyeongnam_company_agent4'),
(20165, 'COMPANY', 4, '11e98756-d7a2-f948-b1b1-0242ac120017', 'gyeongnam_company_agent5'),
(20166, 'COMPANY', 5, '11e98756-d7a2-f948-b1b1-0242ac120017', 'gyeongnam_company_agent6'),
(20167, 'COMPANY', 6, '11e98756-d7a2-f948-b1b1-0242ac120017', 'gyeongnam_company_agent7'),
(20168, 'COMPANY', 7, '11e98756-d7a2-f948-b1b1-0242ac120017', 'gyeongnam_company_agent8'),
(20169, 'COMPANY', 8, '11e98756-d7a2-f948-b1b1-0242ac120017', 'gyeongnam_company_agent9'),
(20170, 'COMPANY', 9, '11e98756-d7a2-f948-b1b1-0242ac120017', 'gyeongnam_company_agent10'),

-- 경상남도 센터 허브 배송 담당자 (HUB 타입)
(10161, 'HUB', 0, '11e98756-d7a2-f948-b1b1-0242ac120017', 'gyeongnam_hub_agent1'),
(10162, 'HUB', 1, '11e98756-d7a2-f948-b1b1-0242ac120017', 'gyeongnam_hub_agent2'),
(10163, 'HUB', 2, '11e98756-d7a2-f948-b1b1-0242ac120017', 'gyeongnam_hub_agent3'),
(10164, 'HUB', 3, '11e98756-d7a2-f948-b1b1-0242ac120017', 'gyeongnam_hub_agent4'),
(10165, 'HUB', 4, '11e98756-d7a2-f948-b1b1-0242ac120017', 'gyeongnam_hub_agent5'),
(10166, 'HUB', 5, '11e98756-d7a2-f948-b1b1-0242ac120017', 'gyeongnam_hub_agent6'),
(10167, 'HUB', 6, '11e98756-d7a2-f948-b1b1-0242ac120017', 'gyeongnam_hub_agent7'),
(10168, 'HUB', 7, '11e98756-d7a2-f948-b1b1-0242ac120017', 'gyeongnam_hub_agent8'),
(10169, 'HUB', 8, '11e98756-d7a2-f948-b1b1-0242ac120017', 'gyeongnam_hub_agent9'),
(10170, 'HUB', 9, '11e98756-d7a2-f948-b1b1-0242ac120017', 'gyeongnam_hub_agent10');

SET search_path TO hubs;

INSERT INTO p_hub_route (id, total_duration, route_distance, from_hub_id, to_hub_id) VALUES
-- 서울특별시 센터 -> 모든 허브 (자신 제외)
('11e9-8756-d7a2-f948-b1b1-0242ac130001', 90, 120.5, '11e98756-d7a2-f948-b1b1-0242ac120001', '11e98756-d7a2-f948-b1b1-0242ac120002'),
('11e9-8756-d7a2-f948-b1b1-0242ac130002', 80, 110.3, '11e98756-d7a2-f948-b1b1-0242ac120001', '11e98756-d7a2-f948-b1b1-0242ac120003'),
('11e9-8756-d7a2-f948-b1b1-0242ac130003', 250, 380.7, '11e98756-d7a2-f948-b1b1-0242ac120001', '11e98756-d7a2-f948-b1b1-0242ac120004'),
('11e9-8756-d7a2-f948-b1b1-0242ac130004', 210, 290.5, '11e98756-d7a2-f948-b1b1-0242ac120001', '11e98756-d7a2-f948-b1b1-0242ac120005'),
('11e9-8756-d7a2-f948-b1b1-0242ac130005', 60, 50.4, '11e98756-d7a2-f948-b1b1-0242ac120001', '11e98756-d7a2-f948-b1b1-0242ac120006'),
('11e9-8756-d7a2-f948-b1b1-0242ac130006', 240, 310.4, '11e98756-d7a2-f948-b1b1-0242ac120001', '11e98756-d7a2-f948-b1b1-0242ac120007'),
('11e9-8756-d7a2-f948-b1b1-0242ac130007', 140, 175.2, '11e98756-d7a2-f948-b1b1-0242ac120001', '11e98756-d7a2-f948-b1b1-0242ac120008'),
('11e9-8756-d7a2-f948-b1b1-0242ac130008', 280, 395.3, '11e98756-d7a2-f948-b1b1-0242ac120001', '11e98756-d7a2-f948-b1b1-0242ac120009'),
('11e9-8756-d7a2-f948-b1b1-0242ac130009', 120, 145.8, '11e98756-d7a2-f948-b1b1-0242ac120001', '11e98756-d7a2-f948-b1b1-0242ac120010'),
('11e9-8756-d7a2-f948-b1b1-0242ac130010', 130, 155.6, '11e98756-d7a2-f948-b1b1-0242ac120001', '11e98756-d7a2-f948-b1b1-0242ac120011'),
('11e9-8756-d7a2-f948-b1b1-0242ac130011', 120, 140.7, '11e98756-d7a2-f948-b1b1-0242ac120001', '11e98756-d7a2-f948-b1b1-0242ac120012'),
('11e9-8756-d7a2-f948-b1b1-0242ac130012', 100, 155.6, '11e98756-d7a2-f948-b1b1-0242ac120001', '11e98756-d7a2-f948-b1b1-0242ac120013'),
('11e9-8756-d7a2-f948-b1b1-0242ac130013', 180, 245.8, '11e98756-d7a2-f948-b1b1-0242ac120001', '11e98756-d7a2-f948-b1b1-0242ac120014'),
('11e9-8756-d7a2-f948-b1b1-0242ac130014', 230, 320.5, '11e98756-d7a2-f948-b1b1-0242ac120001', '11e98756-d7a2-f948-b1b1-0242ac120015'),
('11e9-8756-d7a2-f948-b1b1-0242ac130015', 190, 260.4, '11e98756-d7a2-f948-b1b1-0242ac120001', '11e98756-d7a2-f948-b1b1-0242ac120016'),
('11e9-8756-d7a2-f948-b1b1-0242ac130016', 240, 350.8, '11e98756-d7a2-f948-b1b1-0242ac120001', '11e98756-d7a2-f948-b1b1-0242ac120017'),

-- 경기 북부 센터 -> 모든 허브 (자신 제외)
('11e9-8756-d7a2-f948-b1b1-0242ac130017', 90, 120.5, '11e98756-d7a2-f948-b1b1-0242ac120002', '11e98756-d7a2-f948-b1b1-0242ac120001'),
('11e9-8756-d7a2-f948-b1b1-0242ac130018', 100, 135.6, '11e98756-d7a2-f948-b1b1-0242ac120002', '11e98756-d7a2-f948-b1b1-0242ac120003'),
('11e9-8756-d7a2-f948-b1b1-0242ac130019', 280, 420.3, '11e98756-d7a2-f948-b1b1-0242ac120002', '11e98756-d7a2-f948-b1b1-0242ac120004'),
('11e9-8756-d7a2-f948-b1b1-0242ac130020', 250, 380.5, '11e98756-d7a2-f948-b1b1-0242ac120002', '11e98756-d7a2-f948-b1b1-0242ac120005'),
('11e9-8756-d7a2-f948-b1b1-0242ac130021', 90, 80.4, '11e98756-d7a2-f948-b1b1-0242ac120002', '11e98756-d7a2-f948-b1b1-0242ac120006'),
('11e9-8756-d7a2-f948-b1b1-0242ac130022', 270, 370.2, '11e98756-d7a2-f948-b1b1-0242ac120002', '11e98756-d7a2-f948-b1b1-0242ac120007'),
('11e9-8756-d7a2-f948-b1b1-0242ac130023', 170, 225.4, '11e98756-d7a2-f948-b1b1-0242ac120002', '11e98756-d7a2-f948-b1b1-0242ac120008'),
('11e9-8756-d7a2-f948-b1b1-0242ac130024', 300, 445.8, '11e98756-d7a2-f948-b1b1-0242ac120002', '11e98756-d7a2-f948-b1b1-0242ac120009'),
('11e9-8756-d7a2-f948-b1b1-0242ac130025', 150, 185.3, '11e98756-d7a2-f948-b1b1-0242ac120002', '11e98756-d7a2-f948-b1b1-0242ac120010'),
('11e9-8756-d7a2-f948-b1b1-0242ac130026', 100, 130.5, '11e98756-d7a2-f948-b1b1-0242ac120002', '11e98756-d7a2-f948-b1b1-0242ac120011'),
('11e9-8756-d7a2-f948-b1b1-0242ac130027', 160, 200.3, '11e98756-d7a2-f948-b1b1-0242ac120002', '11e98756-d7a2-f948-b1b1-0242ac120012'),
('11e9-8756-d7a2-f948-b1b1-0242ac130028', 110, 165.7, '11e98756-d7a2-f948-b1b1-0242ac120002', '11e98756-d7a2-f948-b1b1-0242ac120013'),
('11e9-8756-d7a2-f948-b1b1-0242ac130029', 200, 275.4, '11e98756-d7a2-f948-b1b1-0242ac120002', '11e98756-d7a2-f948-b1b1-0242ac120014'),
('11e9-8756-d7a2-f948-b1b1-0242ac130030', 250, 350.2, '11e98756-d7a2-f948-b1b1-0242ac120002', '11e98756-d7a2-f948-b1b1-0242ac120015'),
('11e9-8756-d7a2-f948-b1b1-0242ac130031', 210, 290.8, '11e98756-d7a2-f948-b1b1-0242ac120002', '11e98756-d7a2-f948-b1b1-0242ac120016'),
('11e9-8756-d7a2-f948-b1b1-0242ac130032', 260, 380.2, '11e98756-d7a2-f948-b1b1-0242ac120002', '11e98756-d7a2-f948-b1b1-0242ac120017'),

-- 경기 남부 센터 -> 모든 허브 (자신 제외)
('11e9-8756-d7a2-f948-b1b1-0242ac130033', 80, 110.3, '11e98756-d7a2-f948-b1b1-0242ac120003', '11e98756-d7a2-f948-b1b1-0242ac120001'),
('11e9-8756-d7a2-f948-b1b1-0242ac130034', 100, 135.6, '11e98756-d7a2-f948-b1b1-0242ac120003', '11e98756-d7a2-f948-b1b1-0242ac120002'),
('11e9-8756-d7a2-f948-b1b1-0242ac130035', 260, 400.8, '11e98756-d7a2-f948-b1b1-0242ac120003', '11e98756-d7a2-f948-b1b1-0242ac120004'),
('11e9-8756-d7a2-f948-b1b1-0242ac130036', 230, 350.5, '11e98756-d7a2-f948-b1b1-0242ac120003', '11e98756-d7a2-f948-b1b1-0242ac120005'),
('11e9-8756-d7a2-f948-b1b1-0242ac130037', 70, 75.4, '11e98756-d7a2-f948-b1b1-0242ac120003', '11e98756-d7a2-f948-b1b1-0242ac120006'),
('11e9-8756-d7a2-f948-b1b1-0242ac130038', 250, 360.2, '11e98756-d7a2-f948-b1b1-0242ac120003', '11e98756-d7a2-f948-b1b1-0242ac120007'),
('11e9-8756-d7a2-f948-b1b1-0242ac130039', 150, 210.4, '11e98756-d7a2-f948-b1b1-0242ac120003', '11e98756-d7a2-f948-b1b1-0242ac120008'),
('11e9-8756-d7a2-f948-b1b1-0242ac130040', 280, 425.8, '11e98756-d7a2-f948-b1b1-0242ac120003', '11e98756-d7a2-f948-b1b1-0242ac120009'),
('11e9-8756-d7a2-f948-b1b1-0242ac130041', 130, 175.3, '11e98756-d7a2-f948-b1b1-0242ac120003', '11e98756-d7a2-f948-b1b1-0242ac120010'),
('11e9-8756-d7a2-f948-b1b1-0242ac130042', 120, 140.3, '11e98756-d7a2-f948-b1b1-0242ac120003', '11e98756-d7a2-f948-b1b1-0242ac120011'),
('11e9-8756-d7a2-f948-b1b1-0242ac130043', 100, 120.5, '11e98756-d7a2-f948-b1b1-0242ac120003', '11e98756-d7a2-f948-b1b1-0242ac120012'),
('11e9-8756-d7a2-f948-b1b1-0242ac130044', 110, 165.7, '11e98756-d7a2-f948-b1b1-0242ac120003', '11e98756-d7a2-f948-b1b1-0242ac120013'),
('11e9-8756-d7a2-f948-b1b1-0242ac130045', 170, 255.6, '11e98756-d7a2-f948-b1b1-0242ac120003', '11e98756-d7a2-f948-b1b1-0242ac120014'),
('11e9-8756-d7a2-f948-b1b1-0242ac130046', 220, 330.2, '11e98756-d7a2-f948-b1b1-0242ac120003', '11e98756-d7a2-f948-b1b1-0242ac120015'),
('11e9-8756-d7a2-f948-b1b1-0242ac130047', 190, 270.8, '11e98756-d7a2-f948-b1b1-0242ac120003', '11e98756-d7a2-f948-b1b1-0242ac120016'),
('11e9-8756-d7a2-f948-b1b1-0242ac130048', 240, 360.2, '11e98756-d7a2-f948-b1b1-0242ac120003', '11e98756-d7a2-f948-b1b1-0242ac120017'),

-- 부산광역시 센터 -> 모든 허브 (자신 제외)
('11e9-8756-d7a2-f948-b1b1-0242ac130049', 250, 380.7, '11e98756-d7a2-f948-b1b1-0242ac120004', '11e98756-d7a2-f948-b1b1-0242ac120001'),
('11e9-8756-d7a2-f948-b1b1-0242ac130050', 280, 420.3, '11e98756-d7a2-f948-b1b1-0242ac120004', '11e98756-d7a2-f948-b1b1-0242ac120002'),
('11e9-8756-d7a2-f948-b1b1-0242ac130051', 260, 400.8, '11e98756-d7a2-f948-b1b1-0242ac120004', '11e98756-d7a2-f948-b1b1-0242ac120003'),
('11e9-8756-d7a2-f948-b1b1-0242ac130052', 90, 80.5, '11e98756-d7a2-f948-b1b1-0242ac120004', '11e98756-d7a2-f948-b1b1-0242ac120005'),
('11e9-8756-d7a2-f948-b1b1-0242ac130053', 300, 420.4, '11e98756-d7a2-f948-b1b1-0242ac120004', '11e98756-d7a2-f948-b1b1-0242ac120006'),
('11e9-8756-d7a2-f948-b1b1-0242ac130054', 180, 260.2, '11e98756-d7a2-f948-b1b1-0242ac120004', '11e98756-d7a2-f948-b1b1-0242ac120007'),
('11e9-8756-d7a2-f948-b1b1-0242ac130055', 130, 180.4, '11e98756-d7a2-f948-b1b1-0242ac120004', '11e98756-d7a2-f948-b1b1-0242ac120008'),
('11e9-8756-d7a2-f948-b1b1-0242ac130056', 60, 55.8, '11e98756-d7a2-f948-b1b1-0242ac120004', '11e98756-d7a2-f948-b1b1-0242ac120009'),
('11e9-8756-d7a2-f948-b1b1-0242ac130057', 170, 225.3, '11e98756-d7a2-f948-b1b1-0242ac120004', '11e98756-d7a2-f948-b1b1-0242ac120010'),
('11e9-8756-d7a2-f948-b1b1-0242ac130058', 240, 340.3, '11e98756-d7a2-f948-b1b1-0242ac120004', '11e98756-d7a2-f948-b1b1-0242ac120011'),
('11e9-8756-d7a2-f948-b1b1-0242ac130059', 170, 240.5, '11e98756-d7a2-f948-b1b1-0242ac120004', '11e98756-d7a2-f948-b1b1-0242ac120012'),
('11e9-8756-d7a2-f948-b1b1-0242ac130060', 110, 165.7, '11e98756-d7a2-f948-b1b1-0242ac120004', '11e98756-d7a2-f948-b1b1-0242ac120013'),
('11e9-8756-d7a2-f948-b1b1-0242ac130061', 190, 265.6, '11e98756-d7a2-f948-b1b1-0242ac120004', '11e98756-d7a2-f948-b1b1-0242ac120014'),
('11e9-8756-d7a2-f948-b1b1-0242ac130062', 160, 230.2, '11e98756-d7a2-f948-b1b1-0242ac120004', '11e98756-d7a2-f948-b1b1-0242ac120015'),
('11e9-8756-d7a2-f948-b1b1-0242ac130063', 110, 140.8, '11e98756-d7a2-f948-b1b1-0242ac120004', '11e98756-d7a2-f948-b1b1-0242ac120016'),
('11e9-8756-d7a2-f948-b1b1-0242ac130064', 50, 40.2, '11e98756-d7a2-f948-b1b1-0242ac120004', '11e98756-d7a2-f948-b1b1-0242ac120017'),

-- 대구광역시 센터 -> 모든 허브 (자신 제외)
('11e9-8756-d7a2-f948-b1b1-0242ac130065', 210, 290.5, '11e98756-d7a2-f948-b1b1-0242ac120005', '11e98756-d7a2-f948-b1b1-0242ac120001'),
('11e9-8756-d7a2-f948-b1b1-0242ac130066', 250, 380.5, '11e98756-d7a2-f948-b1b1-0242ac120005', '11e98756-d7a2-f948-b1b1-0242ac120002'),
('11e9-8756-d7a2-f948-b1b1-0242ac130067', 230, 350.5, '11e98756-d7a2-f948-b1b1-0242ac120005', '11e98756-d7a2-f948-b1b1-0242ac120003'),
('11e9-8756-d7a2-f948-b1b1-0242ac130068', 90, 80.5, '11e98756-d7a2-f948-b1b1-0242ac120005', '11e98756-d7a2-f948-b1b1-0242ac120004'),
('11e9-8756-d7a2-f948-b1b1-0242ac130069', 270, 380.4, '11e98756-d7a2-f948-b1b1-0242ac120005', '11e98756-d7a2-f948-b1b1-0242ac120006'),
('11e9-8756-d7a2-f948-b1b1-0242ac130070', 150, 210.2, '11e98756-d7a2-f948-b1b1-0242ac120005', '11e98756-d7a2-f948-b1b1-0242ac120007'),
('11e9-8756-d7a2-f948-b1b1-0242ac130071', 90, 85.4, '11e98756-d7a2-f948-b1b1-0242ac120005', '11e98756-d7a2-f948-b1b1-0242ac120008'),
('11e9-8756-d7a2-f948-b1b1-0242ac130072', 120, 140.8, '11e98756-d7a2-f948-b1b1-0242ac120005', '11e98756-d7a2-f948-b1b1-0242ac120009'),
('11e9-8756-d7a2-f948-b1b1-0242ac130073', 120, 145.3, '11e98756-d7a2-f948-b1b1-0242ac120005', '11e98756-d7a2-f948-b1b1-0242ac120010'),
('11e9-8756-d7a2-f948-b1b1-0242ac130074', 180, 260.3, '11e98756-d7a2-f948-b1b1-0242ac120005', '11e98756-d7a2-f948-b1b1-0242ac120011'),
('11e9-8756-d7a2-f948-b1b1-0242ac130075', 110, 130.5, '11e98756-d7a2-f948-b1b1-0242ac120005', '11e98756-d7a2-f948-b1b1-0242ac120012'),
('11e9-8756-d7a2-f948-b1b1-0242ac130076', 110, 165.7, '11e98756-d7a2-f948-b1b1-0242ac120005', '11e98756-d7a2-f948-b1b1-0242ac120013'),
('11e9-8756-d7a2-f948-b1b1-0242ac130077', 140, 190.6, '11e98756-d7a2-f948-b1b1-0242ac120005', '11e98756-d7a2-f948-b1b1-0242ac120014'),
('11e9-8756-d7a2-f948-b1b1-0242ac130078', 130, 180.2, '11e98756-d7a2-f948-b1b1-0242ac120005', '11e98756-d7a2-f948-b1b1-0242ac120015'),
('11e9-8756-d7a2-f948-b1b1-0242ac130079', 45, 35.8, '11e98756-d7a2-f948-b1b1-0242ac120005', '11e98756-d7a2-f948-b1b1-0242ac120016'),
('11e9-8756-d7a2-f948-b1b1-0242ac130080', 95, 100.2, '11e98756-d7a2-f948-b1b1-0242ac120005', '11e98756-d7a2-f948-b1b1-0242ac120017'),

-- 인천광역시 센터 -> 모든 허브 (자신 제외)
('11e9-8756-d7a2-f948-b1b1-0242ac130081', 60, 50.4, '11e98756-d7a2-f948-b1b1-0242ac120006', '11e98756-d7a2-f948-b1b1-0242ac120001'),
('11e9-8756-d7a2-f948-b1b1-0242ac130082', 90, 80.4, '11e98756-d7a2-f948-b1b1-0242ac120006', '11e98756-d7a2-f948-b1b1-0242ac120002'),
('11e9-8756-d7a2-f948-b1b1-0242ac130083', 70, 75.4, '11e98756-d7a2-f948-b1b1-0242ac120006', '11e98756-d7a2-f948-b1b1-0242ac120003'),
('11e9-8756-d7a2-f948-b1b1-0242ac130084', 300, 420.4, '11e98756-d7a2-f948-b1b1-0242ac120006', '11e98756-d7a2-f948-b1b1-0242ac120004'),
('11e9-8756-d7a2-f948-b1b1-0242ac130085', 270, 380.4, '11e98756-d7a2-f948-b1b1-0242ac120006', '11e98756-d7a2-f948-b1b1-0242ac120005'),
('11e9-8756-d7a2-f948-b1b1-0242ac130086', 290, 410.2, '11e98756-d7a2-f948-b1b1-0242ac120006', '11e98756-d7a2-f948-b1b1-0242ac120007'),
('11e9-8756-d7a2-f948-b1b1-0242ac130087', 180, 235.4, '11e98756-d7a2-f948-b1b1-0242ac120006', '11e98756-d7a2-f948-b1b1-0242ac120008'),
('11e9-8756-d7a2-f948-b1b1-0242ac130088', 310, 430.8, '11e98756-d7a2-f948-b1b1-0242ac120006', '11e98756-d7a2-f948-b1b1-0242ac120009'),
('11e9-8756-d7a2-f948-b1b1-0242ac130089', 150, 180.3, '11e98756-d7a2-f948-b1b1-0242ac120006', '11e98756-d7a2-f948-b1b1-0242ac120010'),
('11e9-8756-d7a2-f948-b1b1-0242ac130090', 145, 175.3, '11e98756-d7a2-f948-b1b1-0242ac120006', '11e98756-d7a2-f948-b1b1-0242ac120011'),
('11e9-8756-d7a2-f948-b1b1-0242ac130091', 150, 185.5, '11e98756-d7a2-f948-b1b1-0242ac120006', '11e98756-d7a2-f948-b1b1-0242ac120012'),
('11e9-8756-d7a2-f948-b1b1-0242ac130092', 30, 25.4, '11e98756-d7a2-f948-b1b1-0242ac120006', '11e98756-d7a2-f948-b1b1-0242ac120013'),
('11e9-8756-d7a2-f948-b1b1-0242ac130093', 220, 300.6, '11e98756-d7a2-f948-b1b1-0242ac120006', '11e98756-d7a2-f948-b1b1-0242ac120014'),
('11e9-8756-d7a2-f948-b1b1-0242ac130094', 270, 380.2, '11e98756-d7a2-f948-b1b1-0242ac120006', '11e98756-d7a2-f948-b1b1-0242ac120015'),
('11e9-8756-d7a2-f948-b1b1-0242ac130095', 230, 320.8, '11e98756-d7a2-f948-b1b1-0242ac120006', '11e98756-d7a2-f948-b1b1-0242ac120016'),
('11e9-8756-d7a2-f948-b1b1-0242ac130096', 280, 400.2, '11e98756-d7a2-f948-b1b1-0242ac120006', '11e98756-d7a2-f948-b1b1-0242ac120017'),

-- 광주광역시 센터 -> 모든 허브 (자신 제외)
('11e9-8756-d7a2-f948-b1b1-0242ac130097', 240, 310.4, '11e98756-d7a2-f948-b1b1-0242ac120007', '11e98756-d7a2-f948-b1b1-0242ac120001'),
('11e9-8756-d7a2-f948-b1b1-0242ac130098', 270, 370.2, '11e98756-d7a2-f948-b1b1-0242ac120007', '11e98756-d7a2-f948-b1b1-0242ac120002'),
('11e9-8756-d7a2-f948-b1b1-0242ac130099', 250, 360.2, '11e98756-d7a2-f948-b1b1-0242ac120007', '11e98756-d7a2-f948-b1b1-0242ac120003'),
('11e9-8756-d7a2-f948-b1b1-0242ac130100', 180, 260.2, '11e98756-d7a2-f948-b1b1-0242ac120007', '11e98756-d7a2-f948-b1b1-0242ac120004'),
('11e9-8756-d7a2-f948-b1b1-0242ac130101', 150, 210.2, '11e98756-d7a2-f948-b1b1-0242ac120007', '11e98756-d7a2-f948-b1b1-0242ac120005'),
('11e9-8756-d7a2-f948-b1b1-0242ac130102', 290, 410.2, '11e98756-d7a2-f948-b1b1-0242ac120007', '11e98756-d7a2-f948-b1b1-0242ac120006'),
('11e9-8756-d7a2-f948-b1b1-0242ac130103', 120, 145.4, '11e98756-d7a2-f948-b1b1-0242ac120007', '11e98756-d7a2-f948-b1b1-0242ac120008'),
('11e9-8756-d7a2-f948-b1b1-0242ac130104', 190, 270.8, '11e98756-d7a2-f948-b1b1-0242ac120007', '11e98756-d7a2-f948-b1b1-0242ac120009'),
('11e9-8756-d7a2-f948-b1b1-0242ac130105', 140, 180.3, '11e98756-d7a2-f948-b1b1-0242ac120007', '11e98756-d7a2-f948-b1b1-0242ac120010'),
('11e9-8756-d7a2-f948-b1b1-0242ac130106', 210, 290.3, '11e98756-d7a2-f948-b1b1-0242ac120007', '11e98756-d7a2-f948-b1b1-0242ac120011'),
('11e9-8756-d7a2-f948-b1b1-0242ac130107', 150, 200.5, '11e98756-d7a2-f948-b1b1-0242ac120007', '11e98756-d7a2-f948-b1b1-0242ac120012'),
('11e9-8756-d7a2-f948-b1b1-0242ac130108', 30, 25.4, '11e98756-d7a2-f948-b1b1-0242ac120007', '11e98756-d7a2-f948-b1b1-0242ac120013'),
('11e9-8756-d7a2-f948-b1b1-0242ac130109', 80, 90.6, '11e98756-d7a2-f948-b1b1-0242ac120007', '11e98756-d7a2-f948-b1b1-0242ac120014'),
('11e9-8756-d7a2-f948-b1b1-0242ac130110', 40, 30.2, '11e98756-d7a2-f948-b1b1-0242ac120007', '11e98756-d7a2-f948-b1b1-0242ac120015'),
('11e9-8756-d7a2-f948-b1b1-0242ac130111', 110, 135.8, '11e98756-d7a2-f948-b1b1-0242ac120007', '11e98756-d7a2-f948-b1b1-0242ac120016'),
('11e9-8756-d7a2-f948-b1b1-0242ac130112', 155, 205.2, '11e98756-d7a2-f948-b1b1-0242ac120007', '11e98756-d7a2-f948-b1b1-0242ac120017'),

-- 대전광역시 센터 -> 모든 허브 (자신 제외)
('11e9-8756-d7a2-f948-b1b1-0242ac130113', 140, 175.2, '11e98756-d7a2-f948-b1b1-0242ac120008', '11e98756-d7a2-f948-b1b1-0242ac120001'),
('11e9-8756-d7a2-f948-b1b1-0242ac130114', 170, 225.4, '11e98756-d7a2-f948-b1b1-0242ac120008', '11e98756-d7a2-f948-b1b1-0242ac120002'),
('11e9-8756-d7a2-f948-b1b1-0242ac130115', 150, 210.4, '11e98756-d7a2-f948-b1b1-0242ac120008', '11e98756-d7a2-f948-b1b1-0242ac120003'),
('11e9-8756-d7a2-f948-b1b1-0242ac130116', 130, 180.4, '11e98756-d7a2-f948-b1b1-0242ac120008', '11e98756-d7a2-f948-b1b1-0242ac120004'),
('11e9-8756-d7a2-f948-b1b1-0242ac130117', 90, 85.4, '11e98756-d7a2-f948-b1b1-0242ac120008', '11e98756-d7a2-f948-b1b1-0242ac120005'),
('11e9-8756-d7a2-f948-b1b1-0242ac130118', 180, 235.4, '11e98756-d7a2-f948-b1b1-0242ac120008', '11e98756-d7a2-f948-b1b1-0242ac120006'),
('11e9-8756-d7a2-f948-b1b1-0242ac130119', 120, 145.4, '11e98756-d7a2-f948-b1b1-0242ac120008', '11e98756-d7a2-f948-b1b1-0242ac120007'),
('11e9-8756-d7a2-f948-b1b1-0242ac130120', 140, 185.8, '11e98756-d7a2-f948-b1b1-0242ac120008', '11e98756-d7a2-f948-b1b1-0242ac120009'),
('11e9-8756-d7a2-f948-b1b1-0242ac130121', 40, 30.3, '11e98756-d7a2-f948-b1b1-0242ac120008', '11e98756-d7a2-f948-b1b1-0242ac120010'),
('11e9-8756-d7a2-f948-b1b1-0242ac130122', 140, 170.3, '11e98756-d7a2-f948-b1b1-0242ac120008', '11e98756-d7a2-f948-b1b1-0242ac120011'),
('11e9-8756-d7a2-f948-b1b1-0242ac130123', 60, 55.5, '11e98756-d7a2-f948-b1b1-0242ac120008', '11e98756-d7a2-f948-b1b1-0242ac120012'),
('11e9-8756-d7a2-f948-b1b1-0242ac130124', 30, 25.4, '11e98756-d7a2-f948-b1b1-0242ac120008', '11e98756-d7a2-f948-b1b1-0242ac120013'),
('11e9-8756-d7a2-f948-b1b1-0242ac130125', 90, 100.6, '11e98756-d7a2-f948-b1b1-0242ac120008', '11e98756-d7a2-f948-b1b1-0242ac120014'),
('11e9-8756-d7a2-f948-b1b1-0242ac130126', 130, 155.2, '11e98756-d7a2-f948-b1b1-0242ac120008', '11e98756-d7a2-f948-b1b1-0242ac120015'),
('11e9-8756-d7a2-f948-b1b1-0242ac130127', 70, 65.8, '11e98756-d7a2-f948-b1b1-0242ac120008', '11e98756-d7a2-f948-b1b1-0242ac120016'),
('11e9-8756-d7a2-f948-b1b1-0242ac130128', 110, 130.2, '11e98756-d7a2-f948-b1b1-0242ac120008', '11e98756-d7a2-f948-b1b1-0242ac120017'),

-- 울산광역시 센터 -> 모든 허브 (자신 제외)
('11e9-8756-d7a2-f948-b1b1-0242ac130129', 280, 395.3, '11e98756-d7a2-f948-b1b1-0242ac120009', '11e98756-d7a2-f948-b1b1-0242ac120001'),
('11e9-8756-d7a2-f948-b1b1-0242ac130130', 300, 445.8, '11e98756-d7a2-f948-b1b1-0242ac120009', '11e98756-d7a2-f948-b1b1-0242ac120002'),
('11e9-8756-d7a2-f948-b1b1-0242ac130131', 280, 425.8, '11e98756-d7a2-f948-b1b1-0242ac120009', '11e98756-d7a2-f948-b1b1-0242ac120003'),
('11e9-8756-d7a2-f948-b1b1-0242ac130132', 60, 55.8, '11e98756-d7a2-f948-b1b1-0242ac120009', '11e98756-d7a2-f948-b1b1-0242ac120004'),
('11e9-8756-d7a2-f948-b1b1-0242ac130133', 120, 140.8, '11e98756-d7a2-f948-b1b1-0242ac120009', '11e98756-d7a2-f948-b1b1-0242ac120005'),
('11e9-8756-d7a2-f948-b1b1-0242ac130134', 310, 430.8, '11e98756-d7a2-f948-b1b1-0242ac120009', '11e98756-d7a2-f948-b1b1-0242ac120006'),
('11e9-8756-d7a2-f948-b1b1-0242ac130135', 190, 270.8, '11e98756-d7a2-f948-b1b1-0242ac120009', '11e98756-d7a2-f948-b1b1-0242ac120007'),
('11e9-8756-d7a2-f948-b1b1-0242ac130136', 140, 185.8, '11e98756-d7a2-f948-b1b1-0242ac120009', '11e98756-d7a2-f948-b1b1-0242ac120008'),
('11e9-8756-d7a2-f948-b1b1-0242ac130137', 180, 260.3, '11e98756-d7a2-f948-b1b1-0242ac120009', '11e98756-d7a2-f948-b1b1-0242ac120010'),
('11e9-8756-d7a2-f948-b1b1-0242ac130138', 245, 345.3, '11e98756-d7a2-f948-b1b1-0242ac120009', '11e98756-d7a2-f948-b1b1-0242ac120011'),
('11e9-8756-d7a2-f948-b1b1-0242ac130139', 180, 250.5, '11e98756-d7a2-f948-b1b1-0242ac120009', '11e98756-d7a2-f948-b1b1-0242ac120012'),
('11e9-8756-d7a2-f948-b1b1-0242ac130140', 30, 25.4, '11e98756-d7a2-f948-b1b1-0242ac120009', '11e98756-d7a2-f948-b1b1-0242ac120013'),
('11e9-8756-d7a2-f948-b1b1-0242ac130141', 200, 275.6, '11e98756-d7a2-f948-b1b1-0242ac120009', '11e98756-d7a2-f948-b1b1-0242ac120014'),
('11e9-8756-d7a2-f948-b1b1-0242ac130142', 170, 240.2, '11e98756-d7a2-f948-b1b1-0242ac120009', '11e98756-d7a2-f948-b1b1-0242ac120015'),
('11e9-8756-d7a2-f948-b1b1-0242ac130143', 90, 95.8, '11e98756-d7a2-f948-b1b1-0242ac120009', '11e98756-d7a2-f948-b1b1-0242ac120016'),
('11e9-8756-d7a2-f948-b1b1-0242ac130144', 70, 70.2, '11e98756-d7a2-f948-b1b1-0242ac120009', '11e98756-d7a2-f948-b1b1-0242ac120017'),

-- 세종특별자치시 센터 -> 모든 허브 (자신 제외)
('11e9-8756-d7a2-f948-b1b1-0242ac130145', 120, 145.8, '11e98756-d7a2-f948-b1b1-0242ac120010', '11e98756-d7a2-f948-b1b1-0242ac120001'),
('11e9-8756-d7a2-f948-b1b1-0242ac130146', 150, 185.3, '11e98756-d7a2-f948-b1b1-0242ac120010', '11e98756-d7a2-f948-b1b1-0242ac120002'),
('11e9-8756-d7a2-f948-b1b1-0242ac130147', 130, 175.3, '11e98756-d7a2-f948-b1b1-0242ac120010', '11e98756-d7a2-f948-b1b1-0242ac120003'),
('11e9-8756-d7a2-f948-b1b1-0242ac130148', 170, 225.3, '11e98756-d7a2-f948-b1b1-0242ac120010', '11e98756-d7a2-f948-b1b1-0242ac120004'),
('11e9-8756-d7a2-f948-b1b1-0242ac130149', 120, 145.3, '11e98756-d7a2-f948-b1b1-0242ac120010', '11e98756-d7a2-f948-b1b1-0242ac120005'),
('11e9-8756-d7a2-f948-b1b1-0242ac130150', 150, 180.3, '11e98756-d7a2-f948-b1b1-0242ac120010', '11e98756-d7a2-f948-b1b1-0242ac120006'),
('11e9-8756-d7a2-f948-b1b1-0242ac130151', 140, 180.3, '11e98756-d7a2-f948-b1b1-0242ac120010', '11e98756-d7a2-f948-b1b1-0242ac120007'),
('11e9-8756-d7a2-f948-b1b1-0242ac130152', 40, 30.3, '11e98756-d7a2-f948-b1b1-0242ac120010', '11e98756-d7a2-f948-b1b1-0242ac120008'),
('11e9-8756-d7a2-f948-b1b1-0242ac130153', 180, 260.3, '11e98756-d7a2-f948-b1b1-0242ac120010', '11e98756-d7a2-f948-b1b1-0242ac120009'),
('11e9-8756-d7a2-f948-b1b1-0242ac130154', 120, 145.3, '11e98756-d7a2-f948-b1b1-0242ac120010', '11e98756-d7a2-f948-b1b1-0242ac120011'),
('11e9-8756-d7a2-f948-b1b1-0242ac130155', 50, 40.5, '11e98756-d7a2-f948-b1b1-0242ac120010', '11e98756-d7a2-f948-b1b1-0242ac120012'),
('11e9-8756-d7a2-f948-b1b1-0242ac130156', 30, 25.4, '11e98756-d7a2-f948-b1b1-0242ac120010', '11e98756-d7a2-f948-b1b1-0242ac120013'),
('11e9-8756-d7a2-f948-b1b1-0242ac130157', 110, 130.6, '11e98756-d7a2-f948-b1b1-0242ac120010', '11e98756-d7a2-f948-b1b1-0242ac120014'),
('11e9-8756-d7a2-f948-b1b1-0242ac130158', 150, 185.2, '11e98756-d7a2-f948-b1b1-0242ac120010', '11e98756-d7a2-f948-b1b1-0242ac120015'),
('11e9-8756-d7a2-f948-b1b1-0242ac130159', 100, 115.8, '11e98756-d7a2-f948-b1b1-0242ac120010', '11e98756-d7a2-f948-b1b1-0242ac120016'),
('11e9-8756-d7a2-f948-b1b1-0242ac130160', 150, 180.2, '11e98756-d7a2-f948-b1b1-0242ac120010', '11e98756-d7a2-f948-b1b1-0242ac120017'),

-- 강원특별자치도 센터 -> 모든 허브 (자신 제외)
('11e9-8756-d7a2-f948-b1b1-0242ac130161', 130, 155.6, '11e98756-d7a2-f948-b1b1-0242ac120011', '11e98756-d7a2-f948-b1b1-0242ac120001'),
('11e9-8756-d7a2-f948-b1b1-0242ac130162', 100, 130.5, '11e98756-d7a2-f948-b1b1-0242ac120011', '11e98756-d7a2-f948-b1b1-0242ac120002'),
('11e9-8756-d7a2-f948-b1b1-0242ac130163', 120, 140.3, '11e98756-d7a2-f948-b1b1-0242ac120011', '11e98756-d7a2-f948-b1b1-0242ac120003'),
('11e9-8756-d7a2-f948-b1b1-0242ac130164', 240, 340.3, '11e98756-d7a2-f948-b1b1-0242ac120011', '11e98756-d7a2-f948-b1b1-0242ac120004'),
('11e9-8756-d7a2-f948-b1b1-0242ac130165', 180, 260.3, '11e98756-d7a2-f948-b1b1-0242ac120011', '11e98756-d7a2-f948-b1b1-0242ac120005'),
('11e9-8756-d7a2-f948-b1b1-0242ac130166', 145, 175.3, '11e98756-d7a2-f948-b1b1-0242ac120011', '11e98756-d7a2-f948-b1b1-0242ac120006'),
('11e9-8756-d7a2-f948-b1b1-0242ac130167', 210, 290.3, '11e98756-d7a2-f948-b1b1-0242ac120011', '11e98756-d7a2-f948-b1b1-0242ac120007'),
('11e9-8756-d7a2-f948-b1b1-0242ac130168', 140, 170.3, '11e98756-d7a2-f948-b1b1-0242ac120011', '11e98756-d7a2-f948-b1b1-0242ac120008'),
('11e9-8756-d7a2-f948-b1b1-0242ac130169', 245, 345.3, '11e98756-d7a2-f948-b1b1-0242ac120011', '11e98756-d7a2-f948-b1b1-0242ac120009'),
('11e9-8756-d7a2-f948-b1b1-0242ac130170', 120, 145.3, '11e98756-d7a2-f948-b1b1-0242ac120011', '11e98756-d7a2-f948-b1b1-0242ac120010'),
('11e9-8756-d7a2-f948-b1b1-0242ac130171', 110, 130.5, '11e98756-d7a2-f948-b1b1-0242ac120011', '11e98756-d7a2-f948-b1b1-0242ac120012'),
('11e9-8756-d7a2-f948-b1b1-0242ac130172', 30, 25.4, '11e98756-d7a2-f948-b1b1-0242ac120011', '11e98756-d7a2-f948-b1b1-0242ac120013'),
('11e9-8756-d7a2-f948-b1b1-0242ac130173', 190, 280.6, '11e98756-d7a2-f948-b1b1-0242ac120011', '11e98756-d7a2-f948-b1b1-0242ac120014'),
('11e9-8756-d7a2-f948-b1b1-0242ac130174', 230, 330.2, '11e98756-d7a2-f948-b1b1-0242ac120011', '11e98756-d7a2-f948-b1b1-0242ac120015'),
('11e9-8756-d7a2-f948-b1b1-0242ac130175', 150, 215.4, '11e98756-d7a2-f948-b1b1-0242ac120011', '11e98756-d7a2-f948-b1b1-0242ac120016'),
('11e9-8756-d7a2-f948-b1b1-0242ac130176', 210, 290.7, '11e98756-d7a2-f948-b1b1-0242ac120011', '11e98756-d7a2-f948-b1b1-0242ac120017'),

-- 충청북도 센터 -> 모든 허브 (자신 제외)
('11e9-8756-d7a2-f948-b1b1-0242ac130177', 120, 140.7, '11e98756-d7a2-f948-b1b1-0242ac120012', '11e98756-d7a2-f948-b1b1-0242ac120001'),
('11e9-8756-d7a2-f948-b1b1-0242ac130178', 160, 200.3, '11e98756-d7a2-f948-b1b1-0242ac120012', '11e98756-d7a2-f948-b1b1-0242ac120002'),
('11e9-8756-d7a2-f948-b1b1-0242ac130179', 100, 120.5, '11e98756-d7a2-f948-b1b1-0242ac120012', '11e98756-d7a2-f948-b1b1-0242ac120003'),
('11e9-8756-d7a2-f948-b1b1-0242ac130180', 170, 240.5, '11e98756-d7a2-f948-b1b1-0242ac120012', '11e98756-d7a2-f948-b1b1-0242ac120004'),
('11e9-8756-d7a2-f948-b1b1-0242ac130181', 110, 130.5, '11e98756-d7a2-f948-b1b1-0242ac120012', '11e98756-d7a2-f948-b1b1-0242ac120005'),
('11e9-8756-d7a2-f948-b1b1-0242ac130182', 150, 185.5, '11e98756-d7a2-f948-b1b1-0242ac120012', '11e98756-d7a2-f948-b1b1-0242ac120006'),
('11e9-8756-d7a2-f948-b1b1-0242ac130183', 150, 200.5, '11e98756-d7a2-f948-b1b1-0242ac120012', '11e98756-d7a2-f948-b1b1-0242ac120007'),
('11e9-8756-d7a2-f948-b1b1-0242ac130184', 60, 55.5, '11e98756-d7a2-f948-b1b1-0242ac120012', '11e98756-d7a2-f948-b1b1-0242ac120008'),
('11e9-8756-d7a2-f948-b1b1-0242ac130185', 180, 250.5, '11e98756-d7a2-f948-b1b1-0242ac120012', '11e98756-d7a2-f948-b1b1-0242ac120009'),
('11e9-8756-d7a2-f948-b1b1-0242ac130186', 50, 40.5, '11e98756-d7a2-f948-b1b1-0242ac120012', '11e98756-d7a2-f948-b1b1-0242ac120010'),
('11e9-8756-d7a2-f948-b1b1-0242ac130187', 110, 130.5, '11e98756-d7a2-f948-b1b1-0242ac120012', '11e98756-d7a2-f948-b1b1-0242ac120011'),
('11e9-8756-d7a2-f948-b1b1-0242ac130188', 30, 25.4, '11e98756-d7a2-f948-b1b1-0242ac120012', '11e98756-d7a2-f948-b1b1-0242ac120013'),
('11e9-8756-d7a2-f948-b1b1-0242ac130189', 120, 155.6, '11e98756-d7a2-f948-b1b1-0242ac120012', '11e98756-d7a2-f948-b1b1-0242ac120014'),
('11e9-8756-d7a2-f948-b1b1-0242ac130190', 160, 210.2, '11e98756-d7a2-f948-b1b1-0242ac120012', '11e98756-d7a2-f948-b1b1-0242ac120015'),
('11e9-8756-d7a2-f948-b1b1-0242ac130191', 90, 100.8, '11e98756-d7a2-f948-b1b1-0242ac120012', '11e98756-d7a2-f948-b1b1-0242ac120016'),
('11e9-8756-d7a2-f948-b1b1-0242ac130192', 140, 170.2, '11e98756-d7a2-f948-b1b1-0242ac120012', '11e98756-d7a2-f948-b1b1-0242ac120017'),

-- 충청남도 센터 -> 모든 허브 (자신 제외)
('11e9-8756-d7a2-f948-b1b1-0242ac130193', 100, 155.6, '11e98756-d7a2-f948-b1b1-0242ac120013', '11e98756-d7a2-f948-b1b1-0242ac120001'),
('11e9-8756-d7a2-f948-b1b1-0242ac130194', 110, 165.7, '11e98756-d7a2-f948-b1b1-0242ac120013', '11e98756-d7a2-f948-b1b1-0242ac120002'),
('11e9-8756-d7a2-f948-b1b1-0242ac130195', 110, 165.7, '11e98756-d7a2-f948-b1b1-0242ac120013', '11e98756-d7a2-f948-b1b1-0242ac120003'),
('11e9-8756-d7a2-f948-b1b1-0242ac130196', 110, 165.7, '11e98756-d7a2-f948-b1b1-0242ac120013', '11e98756-d7a2-f948-b1b1-0242ac120004'),
('11e9-8756-d7a2-f948-b1b1-0242ac130197', 110, 165.7, '11e98756-d7a2-f948-b1b1-0242ac120013', '11e98756-d7a2-f948-b1b1-0242ac120005'),
('11e9-8756-d7a2-f948-b1b1-0242ac130198', 30, 25.4, '11e98756-d7a2-f948-b1b1-0242ac120013', '11e98756-d7a2-f948-b1b1-0242ac120006'),
('11e9-8756-d7a2-f948-b1b1-0242ac130199', 30, 25.4, '11e98756-d7a2-f948-b1b1-0242ac120013', '11e98756-d7a2-f948-b1b1-0242ac120007'),
('11e9-8756-d7a2-f948-b1b1-0242ac130200', 30, 25.4, '11e98756-d7a2-f948-b1b1-0242ac120013', '11e98756-d7a2-f948-b1b1-0242ac120008'),
('11e9-8756-d7a2-f948-b1b1-0242ac130201', 30, 25.4, '11e98756-d7a2-f948-b1b1-0242ac120013', '11e98756-d7a2-f948-b1b1-0242ac120009'),
('11e9-8756-d7a2-f948-b1b1-0242ac130202', 30, 25.4, '11e98756-d7a2-f948-b1b1-0242ac120013', '11e98756-d7a2-f948-b1b1-0242ac120010'),
('11e9-8756-d7a2-f948-b1b1-0242ac130203', 30, 25.4, '11e98756-d7a2-f948-b1b1-0242ac120013', '11e98756-d7a2-f948-b1b1-0242ac120011'),
('11e9-8756-d7a2-f948-b1b1-0242ac130204', 30, 25.4, '11e98756-d7a2-f948-b1b1-0242ac120013', '11e98756-d7a2-f948-b1b1-0242ac120012'),
('11e9-8756-d7a2-f948-b1b1-0242ac130205', 140, 200.3, '11e98756-d7a2-f948-b1b1-0242ac120013', '11e98756-d7a2-f948-b1b1-0242ac120014'),
('11e9-8756-d7a2-f948-b1b1-0242ac130206', 180, 255.9, '11e98756-d7a2-f948-b1b1-0242ac120013', '11e98756-d7a2-f948-b1b1-0242ac120015'),
('11e9-8756-d7a2-f948-b1b1-0242ac130207', 150, 215.4, '11e98756-d7a2-f948-b1b1-0242ac120013', '11e98756-d7a2-f948-b1b1-0242ac120016'),
('11e9-8756-d7a2-f948-b1b1-0242ac130208', 210, 290.7, '11e98756-d7a2-f948-b1b1-0242ac120013', '11e98756-d7a2-f948-b1b1-0242ac120017'),

-- 전북특별자치도 센터 -> 모든 허브 (자신 제외)
('11e9-8756-d7a2-f948-b1b1-0242ac130209', 180, 245.8, '11e98756-d7a2-f948-b1b1-0242ac120014', '11e98756-d7a2-f948-b1b1-0242ac120001'),
('11e9-8756-d7a2-f948-b1b1-0242ac130210', 200, 275.4, '11e98756-d7a2-f948-b1b1-0242ac120014', '11e98756-d7a2-f948-b1b1-0242ac120002'),
('11e9-8756-d7a2-f948-b1b1-0242ac130211', 170, 255.6, '11e98756-d7a2-f948-b1b1-0242ac120014', '11e98756-d7a2-f948-b1b1-0242ac120003'),
('11e9-8756-d7a2-f948-b1b1-0242ac130212', 190, 265.6, '11e98756-d7a2-f948-b1b1-0242ac120014', '11e98756-d7a2-f948-b1b1-0242ac120004'),
('11e9-8756-d7a2-f948-b1b1-0242ac130213', 140, 190.6, '11e98756-d7a2-f948-b1b1-0242ac120014', '11e98756-d7a2-f948-b1b1-0242ac120005'),
('11e9-8756-d7a2-f948-b1b1-0242ac130214', 220, 300.6, '11e98756-d7a2-f948-b1b1-0242ac120014', '11e98756-d7a2-f948-b1b1-0242ac120006'),
('11e9-8756-d7a2-f948-b1b1-0242ac130215', 80, 90.6, '11e98756-d7a2-f948-b1b1-0242ac120014', '11e98756-d7a2-f948-b1b1-0242ac120007'),
('11e9-8756-d7a2-f948-b1b1-0242ac130216', 90, 100.6, '11e98756-d7a2-f948-b1b1-0242ac120014', '11e98756-d7a2-f948-b1b1-0242ac120008'),
('11e9-8756-d7a2-f948-b1b1-0242ac130217', 200, 275.6, '11e98756-d7a2-f948-b1b1-0242ac120014', '11e98756-d7a2-f948-b1b1-0242ac120009'),
('11e9-8756-d7a2-f948-b1b1-0242ac130218', 110, 130.6, '11e98756-d7a2-f948-b1b1-0242ac120014', '11e98756-d7a2-f948-b1b1-0242ac120010'),
('11e9-8756-d7a2-f948-b1b1-0242ac130219', 190, 280.6, '11e98756-d7a2-f948-b1b1-0242ac120014', '11e98756-d7a2-f948-b1b1-0242ac120011'),
('11e9-8756-d7a2-f948-b1b1-0242ac130220', 120, 155.6, '11e98756-d7a2-f948-b1b1-0242ac120014', '11e98756-d7a2-f948-b1b1-0242ac120012'),
('11e9-8756-d7a2-f948-b1b1-0242ac130221', 140, 200.3, '11e98756-d7a2-f948-b1b1-0242ac120014', '11e98756-d7a2-f948-b1b1-0242ac120013'),
('11e9-8756-d7a2-f948-b1b1-0242ac130222', 70, 75.2, '11e98756-d7a2-f948-b1b1-0242ac120014', '11e98756-d7a2-f948-b1b1-0242ac120015'),
('11e9-8756-d7a2-f948-b1b1-0242ac130223', 110, 145.8, '11e98756-d7a2-f948-b1b1-0242ac120014', '11e98756-d7a2-f948-b1b1-0242ac120016'),
('11e9-8756-d7a2-f948-b1b1-0242ac130224', 160, 225.2, '11e98756-d7a2-f948-b1b1-0242ac120014', '11e98756-d7a2-f948-b1b1-0242ac120017'),

-- 전라남도 센터 -> 모든 허브 (자신 제외)
('11e9-8756-d7a2-f948-b1b1-0242ac130225', 230, 320.5, '11e98756-d7a2-f948-b1b1-0242ac120015', '11e98756-d7a2-f948-b1b1-0242ac120001'),
('11e9-8756-d7a2-f948-b1b1-0242ac130226', 250, 350.2, '11e98756-d7a2-f948-b1b1-0242ac120015', '11e98756-d7a2-f948-b1b1-0242ac120002'),
('11e9-8756-d7a2-f948-b1b1-0242ac130227', 220, 330.2, '11e98756-d7a2-f948-b1b1-0242ac120015', '11e98756-d7a2-f948-b1b1-0242ac120003'),
('11e9-8756-d7a2-f948-b1b1-0242ac130228', 160, 230.2, '11e98756-d7a2-f948-b1b1-0242ac120015', '11e98756-d7a2-f948-b1b1-0242ac120004'),
('11e9-8756-d7a2-f948-b1b1-0242ac130229', 130, 180.2, '11e98756-d7a2-f948-b1b1-0242ac120015', '11e98756-d7a2-f948-b1b1-0242ac120005'),
('11e9-8756-d7a2-f948-b1b1-0242ac130230', 270, 380.2, '11e98756-d7a2-f948-b1b1-0242ac120015', '11e98756-d7a2-f948-b1b1-0242ac120006'),
('11e9-8756-d7a2-f948-b1b1-0242ac130231', 40, 30.2, '11e98756-d7a2-f948-b1b1-0242ac120015', '11e98756-d7a2-f948-b1b1-0242ac120007'),
('11e9-8756-d7a2-f948-b1b1-0242ac130232', 130, 155.2, '11e98756-d7a2-f948-b1b1-0242ac120015', '11e98756-d7a2-f948-b1b1-0242ac120008'),
('11e9-8756-d7a2-f948-b1b1-0242ac130233', 170, 240.2, '11e98756-d7a2-f948-b1b1-0242ac120015', '11e98756-d7a2-f948-b1b1-0242ac120009'),
('11e9-8756-d7a2-f948-b1b1-0242ac130234', 150, 185.2, '11e98756-d7a2-f948-b1b1-0242ac120015', '11e98756-d7a2-f948-b1b1-0242ac120010'),
('11e9-8756-d7a2-f948-b1b1-0242ac130235', 230, 330.2, '11e98756-d7a2-f948-b1b1-0242ac120015', '11e98756-d7a2-f948-b1b1-0242ac120011'),
('11e9-8756-d7a2-f948-b1b1-0242ac130236', 160, 210.2, '11e98756-d7a2-f948-b1b1-0242ac120015', '11e98756-d7a2-f948-b1b1-0242ac120012'),
('11e9-8756-d7a2-f948-b1b1-0242ac130237', 180, 255.9, '11e98756-d7a2-f948-b1b1-0242ac120015', '11e98756-d7a2-f948-b1b1-0242ac120013'),
('11e9-8756-d7a2-f948-b1b1-0242ac130238', 70, 75.2, '11e98756-d7a2-f948-b1b1-0242ac120015', '11e98756-d7a2-f948-b1b1-0242ac120014'),
('11e9-8756-d7a2-f948-b1b1-0242ac130239', 140, 180.8, '11e98756-d7a2-f948-b1b1-0242ac120015', '11e98756-d7a2-f948-b1b1-0242ac120016'),
('11e9-8756-d7a2-f948-b1b1-0242ac130240', 140, 190.2, '11e98756-d7a2-f948-b1b1-0242ac120015', '11e98756-d7a2-f948-b1b1-0242ac120017'),

-- 경상북도 센터 -> 모든 허브 (자신 제외)
('11e9-8756-d7a2-f948-b1b1-0242ac130241', 190, 260.4, '11e98756-d7a2-f948-b1b1-0242ac120016', '11e98756-d7a2-f948-b1b1-0242ac120001'),
('11e9-8756-d7a2-f948-b1b1-0242ac130242', 210, 290.8, '11e98756-d7a2-f948-b1b1-0242ac120016', '11e98756-d7a2-f948-b1b1-0242ac120002'),
('11e9-8756-d7a2-f948-b1b1-0242ac130243', 190, 270.8, '11e98756-d7a2-f948-b1b1-0242ac120016', '11e98756-d7a2-f948-b1b1-0242ac120003'),
('11e9-8756-d7a2-f948-b1b1-0242ac130244', 110, 140.8, '11e98756-d7a2-f948-b1b1-0242ac120016', '11e98756-d7a2-f948-b1b1-0242ac120004'),
('11e9-8756-d7a2-f948-b1b1-0242ac130245', 45, 35.8, '11e98756-d7a2-f948-b1b1-0242ac120016', '11e98756-d7a2-f948-b1b1-0242ac120005'),
('11e9-8756-d7a2-f948-b1b1-0242ac130246', 230, 320.8, '11e98756-d7a2-f948-b1b1-0242ac120016', '11e98756-d7a2-f948-b1b1-0242ac120006'),
('11e9-8756-d7a2-f948-b1b1-0242ac130247', 110, 135.8, '11e98756-d7a2-f948-b1b1-0242ac120016', '11e98756-d7a2-f948-b1b1-0242ac120007'),
('11e9-8756-d7a2-f948-b1b1-0242ac130248', 70, 65.8, '11e98756-d7a2-f948-b1b1-0242ac120016', '11e98756-d7a2-f948-b1b1-0242ac120008'),
('11e9-8756-d7a2-f948-b1b1-0242ac130249', 90, 95.8, '11e98756-d7a2-f948-b1b1-0242ac120016', '11e98756-d7a2-f948-b1b1-0242ac120009'),
('11e9-8756-d7a2-f948-b1b1-0242ac130250', 100, 115.8, '11e98756-d7a2-f948-b1b1-0242ac120016', '11e98756-d7a2-f948-b1b1-0242ac120010'),
('11e9-8756-d7a2-f948-b1b1-0242ac130251', 150, 215.4, '11e98756-d7a2-f948-b1b1-0242ac120016', '11e98756-d7a2-f948-b1b1-0242ac120011'),
('11e9-8756-d7a2-f948-b1b1-0242ac130252', 90, 100.8, '11e98756-d7a2-f948-b1b1-0242ac120016', '11e98756-d7a2-f948-b1b1-0242ac120012'),
('11e9-8756-d7a2-f948-b1b1-0242ac130253', 150, 215.4, '11e98756-d7a2-f948-b1b1-0242ac120016', '11e98756-d7a2-f948-b1b1-0242ac120013'),
('11e9-8756-d7a2-f948-b1b1-0242ac130254', 110, 145.8, '11e98756-d7a2-f948-b1b1-0242ac120016', '11e98756-d7a2-f948-b1b1-0242ac120014'),
('11e9-8756-d7a2-f948-b1b1-0242ac130255', 140, 180.8, '11e98756-d7a2-f948-b1b1-0242ac120016', '11e98756-d7a2-f948-b1b1-0242ac120015'),
('11e9-8756-d7a2-f948-b1b1-0242ac130256', 95, 100.5, '11e98756-d7a2-f948-b1b1-0242ac120016', '11e98756-d7a2-f948-b1b1-0242ac120017'),

-- 경상남도 센터 -> 모든 허브 (자신 제외)
('11e9-8756-d7a2-f948-b1b1-0242ac130257', 240, 350.8, '11e98756-d7a2-f948-b1b1-0242ac120017', '11e98756-d7a2-f948-b1b1-0242ac120001'),
('11e9-8756-d7a2-f948-b1b1-0242ac130258', 260, 380.2, '11e98756-d7a2-f948-b1b1-0242ac120017', '11e98756-d7a2-f948-b1b1-0242ac120002'),
('11e9-8756-d7a2-f948-b1b1-0242ac130259', 240, 360.2, '11e98756-d7a2-f948-b1b1-0242ac120017', '11e98756-d7a2-f948-b1b1-0242ac120003'),
('11e9-8756-d7a2-f948-b1b1-0242ac130260', 50, 40.2, '11e98756-d7a2-f948-b1b1-0242ac120017', '11e98756-d7a2-f948-b1b1-0242ac120004'),
('11e9-8756-d7a2-f948-b1b1-0242ac130261', 95, 100.2, '11e98756-d7a2-f948-b1b1-0242ac120017', '11e98756-d7a2-f948-b1b1-0242ac120005'),
('11e9-8756-d7a2-f948-b1b1-0242ac130262', 280, 400.2, '11e98756-d7a2-f948-b1b1-0242ac120017', '11e98756-d7a2-f948-b1b1-0242ac120006'),
('11e9-8756-d7a2-f948-b1b1-0242ac130263', 155, 205.2, '11e98756-d7a2-f948-b1b1-0242ac120017', '11e98756-d7a2-f948-b1b1-0242ac120007'),
('11e9-8756-d7a2-f948-b1b1-0242ac130264', 110, 130.2, '11e98756-d7a2-f948-b1b1-0242ac120017', '11e98756-d7a2-f948-b1b1-0242ac120008'),
('11e9-8756-d7a2-f948-b1b1-0242ac130265', 70, 70.2, '11e98756-d7a2-f948-b1b1-0242ac120017', '11e98756-d7a2-f948-b1b1-0242ac120009'),
('11e9-8756-d7a2-f948-b1b1-0242ac130266', 150, 180.2, '11e98756-d7a2-f948-b1b1-0242ac120017', '11e98756-d7a2-f948-b1b1-0242ac120010'),
('11e9-8756-d7a2-f948-b1b1-0242ac130267', 210, 290.7, '11e98756-d7a2-f948-b1b1-0242ac120017', '11e98756-d7a2-f948-b1b1-0242ac120011'),
('11e9-8756-d7a2-f948-b1b1-0242ac130268', 140, 170.2, '11e98756-d7a2-f948-b1b1-0242ac120017', '11e98756-d7a2-f948-b1b1-0242ac120012'),
('11e9-8756-d7a2-f948-b1b1-0242ac130269', 210, 290.7, '11e98756-d7a2-f948-b1b1-0242ac120017', '11e98756-d7a2-f948-b1b1-0242ac120013'),
('11e9-8756-d7a2-f948-b1b1-0242ac130270', 160, 225.2, '11e98756-d7a2-f948-b1b1-0242ac120017', '11e98756-d7a2-f948-b1b1-0242ac120014'),
('11e9-8756-d7a2-f948-b1b1-0242ac130271', 140, 190.2, '11e98756-d7a2-f948-b1b1-0242ac120017', '11e98756-d7a2-f948-b1b1-0242ac120015'),
('11e9-8756-d7a2-f948-b1b1-0242ac130272', 95, 100.5, '11e98756-d7a2-f948-b1b1-0242ac120017', '11e98756-d7a2-f948-b1b1-0242ac120016');
