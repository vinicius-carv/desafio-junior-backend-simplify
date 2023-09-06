FROM ubuntu:latest
LABEL authors="vinicius"

ENTRYPOINT ["top", "-b"]