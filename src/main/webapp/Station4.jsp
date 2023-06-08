<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" href="station.css"/>
<meta charset="UTF-8">
<title>Station4</title>
</head>
<body>
<h1>STATION 4</h1>
<form action="station" method="post" enctype='multipart/form-data'>
<input type="hidden" name="station" value="4">
<div class="fileinput">
<label for="inputTag">
                        <input id="inputTag" type="file" name="file"/>
                        <img src="fingerprint.png" alt="fingerprint">
                        </label>
                       	</div>
<button type="submit">
Scan your fingerprint
</button>
</form>
</body>
</html>