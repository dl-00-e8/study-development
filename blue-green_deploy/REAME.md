# 블루 그린 무중단 배포 실습

### 1. 서버를 도커로 띄우기 위한 Dockerfile을 작성해주세요.

루트 디렉토리에 `.Dockerfile`을 생성해주시면 됩니다.

### 2. 도커 이미지를 생성해주세요.

`docker build -t <이미지 이름> .`

### 2. 무중단 배포에 활용하기 위한 Docker Compose 파일을 작성해주세요.


### 3. Blue 컨테이너를 띄워주세요.

`docker-compose up blue`

### 4. 무중단 배포를 위한 Shell Script를 작성해주세요.

Shell script를 실행은 다음과 같이 하실 수 있습니다. `sh myscript.sh`

### 5. 정상적으로 Green Container가 실행되었는지 확인해주세요.