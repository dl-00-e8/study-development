import boto3
from botocore.exceptions import NoCredentialsError, PartialCredentialsError

def upload_to_s3(file_path, bucket_name, access_key, secret_key):
    # 파일 이름 추출 (경로에서 파일명만 가져오기)
    file_name = file_path.split('/')[-1]

    try:
        # S3 클라이언트 생성
        s3_client = boto3.client(
            's3',
            aws_access_key_id=access_key,
            aws_secret_access_key=secret_key,
            region_name='ap-northeast-2'  # 서울 리전
        )

        # 파일 읽기
        with open(file_path, 'rb') as file:
            # 메타데이터 설정
            object_metadata = {
                'ContentType': 'text/plain',  # txt 파일의 경우
            }
            file.seek(0)

            # S3에 업로드
            s3_client.upload_fileobj(
                file,
                bucket_name,
                file_name,
                ExtraArgs=object_metadata
            )
            print(f"File uploaded successfully: {file_name}")

    except FileNotFoundError:
        print("The file was not found.")
    except NoCredentialsError:
        print("Credentials not available.")
    except PartialCredentialsError:
        print("Incomplete credentials provided.")
    except Exception as e:
        print(f"An error occurred: {e}")

# Usage
file_path = '/Users/jeongjin/Desktop/Git/study-development/s3-file-upload/acc-hongik.txt'
bucket_name = 'acc-hongik-bucket'
access_key = ''
secret_key = ''

upload_to_s3(file_path, bucket_name, access_key, secret_key)