<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<c:set var="pageName" value="게시물 상세" />
<%@ include file="../part/head.jspf"%>

<script>
	var id = parseInt('${article.id}');
</script>

<script>
	function ViewArticle1__updateLikePoint(newLikePoint) {
		$('.article--like-point').empty().append(newLikePoint);
	}
	function callDoLike() {
		if (confirm('추천 하시겠습니까?') == false) {
			return;
		}
		$.post('./doLikeAjax', {
			id : id
		}, function(data) {
			if (data.resultCode.substr(0, 2) == 'S-') {
				ViewArticle1__updateLikePoint(data.likePoint);
			} else {
				if (data.msg) {
					alert(data.msg);
				}
			}
		}, 'json');
	}
	function callDoCancelLike() {
		if (confirm('추천을 취소 하시겠습니까?') == false) {
			return;
		}
		$.post('./doCancelLikeAjax', {
			id : id
		}, function(data) {
			if (data.resultCode.substr(0, 2) == 'S-') {
				ViewArticle1__updateLikePoint(data.likePoint);
			} else {
				if (data.msg) {
					alert(data.msg);
				}
			}
		}, 'json');
	}
</script>

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
				<th>작성자</th>
				<td>${article.extra.writer}</td>
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
				<th>좋아요</th>
				<td><span class="article--like-point">${article.extra.likePoint}</span>
					/ <a href="#" onclick="callDoLike();">좋아요</a> <a href="#"
					onclick="callDoCancelLike();">좋아요취소</a></td>
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

<c:if test="${isLogined}">
	<h2 class="con">댓글 작성</h2>

	<form action="./doWriteReply" method="POST">
		<input type="hidden" name="articleId" value="${article.id}" /> <input
			type="hidden" name="redirectUrl" value="${requestUriQueryString}" />
		<div class="table-box con">
			<table>
				<tbody>
					<tr>
						<th>내용</th>
						<td><textarea maxlength="300" class="height-100px"
								name="body" placeholder="내용을 입력해주세요."></textarea></td>
					</tr>
					<tr>
						<th>작성</th>
						<td><input type="submit" value="작성"></td>
					</tr>
				</tbody>
			</table>
		</div>
	</form>

</c:if>

<h2 class="con">댓글 리스트</h2>

<div class="table-box con">
	<table>
		<colgroup>
			<col width="80">
			<col width="180">
			<col width="180">
			<col>
			<col width="200">
		</colgroup>
		<thead>
			<tr>
				<th>번호</th>
				<th>날짜</th>
				<th>작성자</th>
				<th>내용</th>
				<th>비고</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${articleReplies}" var="articleReply">
				<tr>
					<td>${articleReply.id}</td>
					<td>${articleReply.regDate}</td>
					<td>${articleReply.extra.writer}</td>
					<td>${articleReply.body}</td>
					<td><a
						href="./doDeleteReply?id=${articleReply.id}&redirectUrl=${urlEncodedRequestUriQueryString}"
						onclick="if ( confirm('삭제하시겠습니까?') == false ) { return false; }">삭제</a>

						<a
						href="./modifyReply?id=${articleReply.id}&redirectUrl=${urlEncodedRequestUriQueryString}">수정</a>
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</div>

<%@ include file="../part/foot.jspf"%>