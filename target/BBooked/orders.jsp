<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix = "ex" uri = "WEB-INF/tld/custom.tld"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" isELIgnored="false" %>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Orders</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">

    <style>
    .checked {
      color: orange;
    }
    </style>

    <script>
      function submitOrder(x) {
        window.location.replace("./order?id=" + x);
      };

      function editTour(x) {
        window.location.replace("./editTour?id=" + x);
      };
    </script>


</head>
<body>

<h3><fmt:message key="orders_jsp.message" /></h3>
<br/>
<div class="card container">
    <div class="row">
      <div class="col">
        <fmt:message key="orders_jsp.status" />
      </div>
      <div class="col">
        <fmt:message key="tours_jsp.tourName" />
      </div>
      <div class="col">
        <fmt:message key="tours_jsp.tourDescription" />
      </div>
      <div class="col">
        <fmt:message key="tours_jsp.tourPrice" />
      </div>
      <div class="col">
        <fmt:message key="tours_jsp.personsMinimum" />
      </div>
    </div>
    <c:forEach items="${tours}" var="tour">
        <span class="border row">
            <div class="col">
              ${tour.status}
            </div>
            <div class="col">
                ${tour.name}</br>
                <c:forEach begin="1" end="${tour.hotel}">
                    <span class="fa fa-star checked"></span>
                </c:forEach>
                <c:if test="${tour.hotel < 5}">
                    <c:forEach begin="${tour.hotel + 1}" end="5">
                        <span class="fa fa-star"></span>
                    </c:forEach>
                </c:if>
            </div>
            <div class="col">
                ${tour.description}
            </div>
            <div class="col">
              ${tour.price}
            </div>
            <div class="col">
                <c:if test="${tour.person == 1}">
                    <span class="fa fa-user" aria-hidden="true"></span>
                </c:if>
                <c:if test="${tour.person > 1}">
                    <c:forEach begin="1" end="${tour.person}">
                        <span class="fa fa-user" aria-hidden="true"></span>
                    </c:forEach>
                </c:if>
            </div>

        </span>
    </c:forEach>
</div>

<ex:Extra/>
</body>
</html>
