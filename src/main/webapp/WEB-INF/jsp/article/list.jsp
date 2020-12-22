<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<c:set var="pageName" value="${board.name} 게시물 리스트" />
<%@ include file="../part/head.jspf"%>

<div class="table-box con">
	<table>
		<colgroup>
			<col width="80">
			<col width="180">
			<col width="180">
			<col width="80">
			<col>
			<col width="200">
		</colgroup>
		<thead>
			<tr>
				<th>번호</th>
				<th>날짜</th>
				<th>작성자</th>
				<th>조회수</th>
				<th>좋아요</th>
				<th>제목</th>
				<th>비고</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${articles}" var="article">
				<tr>
					<td>${article.id}</td>
					<td>${article.regDate}</td>
					<td>${article.extra.writer}</td>
					<td>${article.hit}</td>
					<td>${article.extra.likePoint}</td>
					<td><a href="./detail?id=${article.id}">${article.title}</a></td>
					<td>
						<c:if test="${article.extra.loginedMemberCanLike}">
							<a
							href="./doLike?id=${article.id}&redirectUrl=/article/list?boardCode=${board.code}"
							onclick="if ( confirm('추천하시겠습니까?') == false ) { return false; }">좋아요</a>
						</c:if>
						
						<c:if test="${article.extra.loginedMemberCanCancelLike}">
							<a
							href="./doCancelLike?id=${article.id}&redirectUrl=/article/list?boardCode=${board.code}"
							onclick="if ( confirm('추천을 취소하시겠습니까?') == false ) { return false; }">좋아요취소</a>
						</c:if>
						
						<a href="./doDelete?id=${article.id}"
						onclick="if ( confirm('삭제하시겠습니까?') == false ) { return false; }">삭제</a>
						<a href="./modify?id=${article.id}">수정</a></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</div>

<%@ include file="../part/foot.jspf"%>