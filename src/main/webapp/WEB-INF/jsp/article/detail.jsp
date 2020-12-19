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
			</tr>
			<tr>
				<th>조회수</th>
				<td>${article.hit}</td>
			</tr>
			<tr>
				<th>제목</th>
				<td>${article.title}</td>
			</tr>
			<tr>
				<th>내용</th>
				<td>${article.body}</td>
			</tr>
			<tr>
				<th>비고</th>
				<td><a href="./doDelete?id=${article.id}"
					onclick="if ( confirm('삭제하시겠습니까?') == false ) { return false; }">삭제</a>
					<a href="./modify?id=${article.id}">수정</a></td>
			</tr>
		</tbody>
	</table>
</div>

<%@ include file="../part/foot.jspf"%>