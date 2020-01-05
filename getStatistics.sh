while true
do
  curl -o - http://localhost:8080/statistics; echo
  sleep 1
done