<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix = "ex" uri = "WEB-INF/tld/custom.tld"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@page pageEncoding="UTF-8" %>
<fmt:setBundle basename="resources"/>
<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" isELIgnored="false" %>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="content-type" content="text/html; charset=utf-8">
    <title>Hello JSP!!!</title>
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

      function setHot(x) {
        window.location.replace("./setHot?id=" + x);
      };
    </script>


</head>
<body>

    <form action="./tours" method="GET">
      <div class="row">
        <div class="form-group col">
            <label for="hotel"><fmt:message key="tours_jsp.hotelRating" /></label>
            <select class="form-control" name="hotel">
                <option> 1-star </option>
                <option> 2-star </option>
                <option> 3-star </option>
                <option> 4-star </option>
                <option> 5-star </option>
            </select>
        </div>
        <div class="form-group col">
            <label for="person"><fmt:message key="tours_jsp.personsMinimum" /></label>
            <select class="form-control" name="person">
                <option> 1 person</option>
                <option> 2 persons</option>
                <option> 3 persons</option>
                <option> 4 persons</option>
                <option> 5 persons</option>
            </select>
        </div>
        <div class="form-group col">
            <label for="min"><fmt:message key="tours_jsp.minPrice" /></label>
            <input type="number" class="form-control" name="min">
        </div>
        <div class="form-group col">
            <label for="max"><fmt:message key="tours_jsp.maxPrice" /></label>
            <input type="number" class="form-control" name="max">
        </div>
        <div class="form-group col">
        <label for="tags"><fmt:message key="tours_jsp.pickTags" /></label>
            <select multiple class="form-select" size="3" name="tags">
                <option>rest</option>
                <option>excursion</option>
                <option>shopping</option>
            </select>
        </div>
        <div class="form-group col">
            <button type="submit" class="btn btn-primary"><fmt:message key="tours_jsp.submit" /></button>
            <c:if test="${role == 'root'}">
              <br/><a href="addTour"><fmt:message key="tours_jsp.addTour" /></a>
            </c:if>
        </div>
        <div class="col">
            <a href="./setEng.jsp">EN</a>
            <a href="./setUkr.jsp">UA</a>
        </div>
      </div>
    </form>
<hr />
<br/>
<div class="card container">
    <div class="card-body row">
      <c:if test="${role == 'root'}">
        <div class="col">
          Status
        </div>
      </c:if>

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
      <div class="col">
        <fmt:message key="tours_jsp.userAction" />
      </div>
    </div>
    <c:forEach items="${tours}" var="tour">
        <span class="border row">
          <c:if test="${role == 'root'}">
            <div class="col">
              ${tour.status}
            </div>
          </c:if>
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
            <div class="col">
              <c:if test="${sessionScope.role == 'guest'}">
                <fmt:message key="tours_jsp.logInToOrder" />
              </c:if>
              <c:if test="${sessionScope.role == 'user'}">
                <button class="btn btn-primary" onClick='submitOrder(${tour.id})' ><fmt:message key="tours_jsp.order" /></button>
              </c:if>
              <c:if test="${sessionScope.role == 'root'}">
                <button class="btn btn-primary" onClick='editTour(${tour.id})' ><fmt:message key="tours_jsp.editTour" /></button>
              </c:if>
              <c:if test="${(sessionScope.role == 'manager' || sessionScope.role == 'root') && !tour.hot}">
                  <button class="btn btn-primary" onClick='setHot(${tour.id})' ><fmt:message key="tours_jsp.setHot" /></button>
              </c:if>
              <c:if test="${(sessionScope.role == 'manager' || sessionScope.role == 'root') && tour.hot}">
                <button class="btn btn-primary" onClick='setHot(${tour.id})' ><fmt:message key="tours_jsp.unsetHot" /></button>
              </c:if>

            </div>
        </span>
    </c:forEach>
</div>

<ex:Extra/>

</body>
</html>
