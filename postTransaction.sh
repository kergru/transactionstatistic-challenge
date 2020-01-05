while true
do
  curl -d "sales_amount=25.33" -X POST http://localhost:8080/sales
done