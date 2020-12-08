<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<c:set var="pageName" value="게시물 상세" />
<%@ include file="../part/head.jspf"%>

<div class="table-box con">
	<table>
		<colgroup>
			<col width="180">
			<col>
		</colgroup>
		<tbody>
			<tr>
				<th>번호</th>
				<td>${article.id}</td>
			</tr>
			<tr>
				<th>날짜</th>
				<td>${article.regDate}</td>
			<tr>
				<th>제목</th>
				<td>${article.title}</td>
			<tr>
				<th>내용</th>
				<td>${article.body}</td>
			<tr>
				<th>번호</th>
				<td><a href="#">삭제</a></td>
		</tbody>
	</table>
</div>

<%@ include file="../part/foot.jspf"%>
