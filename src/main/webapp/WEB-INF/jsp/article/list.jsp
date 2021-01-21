<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<c:set var="pageName" value="${board.name} 게시물 리스트" />
<%@ include file="../part/head.jspf"%>

<div class="con margin-top-30">
	<span>
		<span>총 게시물 수 : </span>
		<span>${totalCount}</span>
	</span>
	<span>/</span>
	<span>
		<span>현재 페이지 : </span>
		<span style="color:red;">${param.page}</span>
	</span>
</div>

<div class="table-box margin-top-30 con">
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
							href="./doLike?id=${article.id}&redirectUrl=${urlEncodedRequestUriQueryString}"
							onclick="if ( confirm('추천하시겠습니까?') == false ) { return false; }">좋아요</a>
						</c:if>
						
						<c:if test="${article.extra.loginedMemberCanCancelLike}">
							<a
							href="./doCancelLike?id=${article.id}&redirectUrl=${urlEncodedRequestUriQueryString}"
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

<!-- URL 초기값 -->
<c:url var="pageUrl" value="">
	<c:forEach items="${param}" var="entry">
		<c:if test="${entry.key != 'page'}">
			<c:param name="${entry.key}" value="${entry.value}" />
		</c:if>
	</c:forEach>
</c:url>

<div class="page-menu text-align-center margin-top-30">
	<c:forEach begin="1" end="${totalPage}" var="currentPage">
		<c:if test="${currentPage == param.page}">
			<a style="color:red; font-weight:bold;" href="${pageUrl}&page=${currentPage}">${currentPage}</a>
		</c:if>
		<c:if test="${currentPage != param.page}">
			<a href="${pageUrl}&page=${currentPage}">${currentPage}</a>
		</c:if>
	</c:forEach>
</div>

<%@ include file="../part/foot.jspf"%>
