<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<c:set var="pageName" value="게시물 리스트" />
<%@ include file="../part/head.jspf"%>

<div class="con">
	<c:forEach items="${articles}" var="article">
		<section>
			<a href="./detail?id=${article.id}">번호 : ${article.id}, 제목 :
				${article.title}</a>
		</section>
		<hr>
	</c:forEach>
</div>

<%@ include file="../part/foot.jspf"%> 