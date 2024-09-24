docker pull postgres:10.21-alpine

docker run -d \
	-p 5432:5432 \
	-v /root/datamapping/postgres/pgdata:/var/lib/postgresql/data \
	-e POSTGRES_PASSWORD=root \
	--name pgsql \
	postgres:10.21-alpine

docker update pgsql --restart=always