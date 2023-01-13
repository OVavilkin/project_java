<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix = "ex" uri = "WEB-INF/tld/custom.tld"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" isELIgnored="false" %>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Manager</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">

    <style>
    .checked {
      color: orange;
    }
    </style>


</head>
<body>


<div class="container">
    <c:forEach items="${tours}" var="tour">
        <span class="border row">
            <div class="col">
              <form action="./manager" method="POST">
                <input type="hidden" name="id" value="${tour.id}" />
                <fmt:message key="manager_jsp.currentStatus" /> ${tour.status} <br />
                <fmt:message key="manager_jsp.registeredTo" /> ${tour.user.username} <br /><br />
                <select class="form-select" name="status">
                  <option selected><fmt:message key="manager_jsp.changeStatus" /></option>
                  <option value="cancelled"><fmt:message key="manager_jsp.cancel" /></option>
                  <option value="paid"><fmt:message key="manager_jsp.paid" /></option>
                </select>
                <label for="percent"><fmt:message key="manager_jsp.discount" /></label>
                <input type="text" name="percent" value="${tour.percent}"></input>
              <button type="submit" class="btn btn-primary"><fmt:message key="manager_jsp.submit" /></button>
              </form>
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
