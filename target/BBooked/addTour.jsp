<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix = "ex" uri = "WEB-INF/tld/custom.tld"%>

<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" isELIgnored="false" %>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Add Tour</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">

</head>
<body>
<h3>Add a tour</h3>
  <form action="./addTour" method="POST">
    <div class="input-group mb-3">
      <div class="input-group-pretend">
        <span class="input-group-text" id="tourNameLablel">Tour name</span>
      </div>
      <input type="text" class="form-control" name="name"></input>
    </div>
    <div class="input-group mb-3">
      <div class="input-group-pretend">
        <span class="input-group-text" id="tourDescLabel">Tour Description</span>
      </div>
      <textarea class="form-control" name="description">Some description here</textarea>
    </div>
     <div class="input-group mb-3">
       <div class="input-group-prepend">
         <span class="input-group-text" id="hotelDescLabel">Hotel Rating</span>
       </div>
       <select class="custom-select" name="hotel">
         <option selected>1</option>
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
       <input type="number" class="form-control" name="person"></input>
     </div>
      <div class="input-group mb-3">
       <div class="input-group-prepend">
         <span class="input-group-text" id="priceDescLabel">Tour price</span>
       </div>
       <input type="number" class="form-control" name="price"></input>
     </div>

     <div class="input-group mb-3">
       <div class="input-group-prepend">
         <span class="input-group-text" id="tagsDescLabel">Tag(s)</span>
       </div>
       <select multiple class="form-select" size="3" name="tags">
         <option selected>rest</option>
         <option>excursion</option>
         <option>shopping</option>
       </select>
     </div>
     <br/>
    <button class="btn btn-primary" type="submit">Add Tour</button>
  </form>

  <a href="./tours"> go back </a>
  <ex:Extra/>
</body>
</html>
