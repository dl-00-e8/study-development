# 가벼운 Python 이미지 사용 (Debian 기반 → Alpine으로 변경)
FROM python:3.9-alpine

# 작업 디렉토리 설정
WORKDIR /app

# 필요한 파일만 복사
COPY server.py .

# 의존성 설치 (Alpine에 맞춰 변경)
RUN pip install --no-cache-dir flask

EXPOSE 5001

# 컨테이너 실행 시 실행할 명령어
CMD ["python", "server.py"]