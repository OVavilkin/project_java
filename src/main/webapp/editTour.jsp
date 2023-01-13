<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix = "ex" uri = "WEB-INF/tld/custom.tld"%>

<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" isELIgnored="false" %>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Edit Tour</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">

</head>
<body>
<h3>Edit Tour</h3>
  <form action="./editTour" method="POST">
    <input type="hidden" name="id" value="${tour.id}" />

    <div class="input-group mb-3">
      <div class="input-group-pretend">
        <span class="input-group-text" id="tourNameLablel">Tour name</span>
      </div>
      <input type="text" class="form-control" name="name" value="${tour.name}"></input>
    </div>
    <div class="input-group mb-3">
      <div class="input-group-pretend">
        <span class="input-group-text" id="tourDescLabel">Tour Description</span>
      </div>
      <textarea class="form-control" name="description">${tour.description}</textarea>
    </div>
     <div class="input-group mb-3">
       <div class="input-group-prepend">
         <span class="input-group-text" id="hotelDescLabel">Hotel Rating</span>
       </div>
       <select class="custom-select" name="hotel">
         <option>1</option>
         <option>2</option>
         <option>3</option>
         <option>4</option>
         <option>5</option>
       </select>
     </div>
     <div class="input-group mb-3">
       <div class="input-group-prepend">
         <span class="input-group-text" id="personDescLabel">Number of people</span>
       </div>
       <input type="number" class="form-control" name="person" value="${tour.person}"></input>
     </div>
     <div class="input-group mb-3">
       <div class="input-group-prepend">
         <span class="input-group-text" id="tagsDescLabel">Tag(s)</span>
       </div>
       <select multiple class="form-select" size="3" name="tags">
         <option>rest</option>
         <option>excursion</option>
         <option>shopping</option>
       </select>
     </div>
     <div class="input-group mb-3">
      <c:if test="${tour.hot}">
        <input class="form-check-input" type="checkbox" value="" name="hot" checked>
      </c:if>
      <c:if test="${!tour.hot}">
        <input class="form-check-input" type="checkbox" value="" name="hot">
      </c:if>
         <label class="form-check-label" for="hot">
           Set as Hot!
         </label>
     </div>
     <br/>
    <button class="btn btn-primary" type="submit">Save Tour</button> / <a href="./tours"> go back </a>
  </form>

 <ex:Extra/>
</body>
</html>
