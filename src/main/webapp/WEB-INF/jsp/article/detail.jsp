<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<c:set var="pageName" value="게시물 상세" />
<%@ include file="../part/head.jspf"%>

<div class="con">
	<div>번호 : ${article.id}</div>
	<div>제목 : ${article.title}</div>
</div>

<%@ include file="../part/foot.jspf"%> 