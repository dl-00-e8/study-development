#!/bin/bash

IS_GREEN=$(sudo docker ps | grep green) # 현재 실행중인 App이 green인지 확인합니다.
DEFAULT_CONF="./"
BLUE_HEALTHCHECK="http://127.0.0.1:5001/"
GREEN_HEALTHCHECK="http://127.0.0.1:5002/"

if [ -z $IS_GREEN  ];then # blue라면

  echo "### BLUE => GREEN ###"

  echo "1. get green image"
  sudo docker compose pull green # green으로 이미지를 내려받습니다.

  echo "2. green container up"
  sudo docker compose up -d green # green 컨테이너 실행

  while [ 1 = 1 ]; do
  echo "3. green health check..."
  sleep 3

  REQUEST=$(curl $GREEN_HEALTHCHECK) # green request
    if [ -n "$REQUEST" ]; then # 서비스 가능하면 health check 중지
            echo "health check success"
            break ;
            fi
  done;

#   echo "4. reload nginx"
#   sudo cp /etc/nginx/green_nginx.conf /etc/nginx/nginx.conf
#   sudo nginx -s reload

  echo "5. blue container down"
  sudo docker compose stop blue
else
  echo "### GREEN => BLUE ###"

  echo "1. get blue image"
  sudo docker compose pull blue # blue로 이미지를 내려받습니다.

  echo "2. blue container up"
  sudo docker compose up -d blue

  while [ 1 = 1 ]; do
    echo "3. blue health check..."
    sleep 3

    REQUEST=$(curl $BLUE_HEALTHCHECK) # blue으로 request

    if [ -n "$REQUEST" ]; then # 서비스 가능하면 health check 중지
      echo "health check success"
      break ;
    fi
  done;

#   echo "4. reload nginx" 
#   sudo cp /etc/nginx/blue_nginx.conf /etc/nginx/nginx.conf
#   sudo nginx -s reload

  echo "5. green container down"
  sudo docker compose stop green
fi