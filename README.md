# PhotoSend Server

## Overview
https://www.notion.so/Photosend-66cb21c479404c9eb86792197e5104b2

## Setting
#### aws 설정 (s3)
1. /root에 `.aws` 디렉토리 생성
2. `.aws` 디렉토리 하위에 credentials 파일 생성
3. s3에 접근 가능한 IAM User를 만들어, 해당 정보를 아래와 같이 입력 (credentials 정보는 aws IAM의 사용자탭의 developer)
```
[default]
aws_access_key_id = ...
aws_secret_access_key = ...
```


#### photosend 설정(database,  firebase)
1. /root에 `.photosend` 디렉토리 생성



#### database 설정
2. `.photosend` 디렉토리 하위에 databaseAccount.txt 파일 생성
3. 파일 내부에 database 유저정보에 맞게 아래내용 기입
```
username= ...
password= ...
```


#### firebase 설정
4. fire base에서 project key 다운로드(peoplusapply@gmail.com으로 로그인 -> 프로젝트 설정 -> 서비스 계정 -> 새비공개키 생성)
5. firebase에서 생성한 project key를 `.photosend` 디렉토리 하위로 이동 




## database
1. mysql 8 설치
2. create database photosend (photosend database 생성)




## Getting Started
1. search (실행중인 java 프로세스 확인 alias)
2. kj (실행중인 java 프로세스 종료)
3. run (application 실행, 백그라운드 실행시 "run &")