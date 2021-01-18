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

	<script>
		function ArticleReply__submitWriteForm(form) {
			form.body.value = form.body.value.trim();
			if (form.body.value.length == 0) {
				alert('댓글을 입력해주세요.');
				form.body.focus();
				return;
			}
			$.post('./doWriteReplyAjax', {
				articleId : param.id,
				body : form.body.value
			}, function(data) {
			}, 'json');
			form.body.value = '';
		}
	</script>

	<form action=""
		onsubmit="ArticleReply__submitWriteForm(this); return false;">
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

<script>
	var ArticleReply__lastLoadedArticleReplyId = 0;
	function ArticleReply__loadList() {
		$.get('./getForPrintArticleRepliesRs', {
			id : param.id,
			from : ArticleReply__lastLoadedArticleReplyId + 1
		}, function(data) {
			data.articleReplies = data.articleReplies.reverse();
			for (var i = 0; i < data.articleReplies.length; i++) {
				var articleReply = data.articleReplies[i];
				ArticleReply__drawReply(articleReply);
				ArticleReply__lastLoadedArticleReplyId = articleReply.id;
			}
			setTimeout(ArticleReply__loadList, 1000);
		}, 'json');
	}
	var ArticleReply__$listTbody;
	function ArticleReply__drawReply(articleReply) {
		var html = $('.template-box-1 tbody').html();
		html = replaceAll(html, "{$번호}", articleReply.id);
		html = replaceAll(html, "{$날짜}", articleReply.regDate);
		html = replaceAll(html, "{$작성자}", articleReply.extra.writer);
		html = replaceAll(html, "{$내용}", articleReply.body);
		/*
		var html = '';
		html = '<tr data-article-reply-id="' + articleReply.id + '">';
		html += '<td>' + articleReply.id + '</td>';
		html += '<td>' + articleReply.regDate + '</td>';
		html += '<td>' + articleReply.extra.writer + '</td>';
		html += '<td>' + articleReply.body + '</td>';
		html += '<td>';
		html += '<a href="#">삭제</a>';
		html += '<a href="#">수정</a>';
		html += '</td>';
		html += '</tr>';
		 */
		ArticleReply__$listTbody.prepend(html);
	}
	$(function() {
		ArticleReply__$listTbody = $('.article-reply-list-box > table tbody');
		ArticleReply__loadList();
	});
	function ArticleReply__delete(obj) {
		alert(obj);
	}
</script>

<div class="template-box template-box-1">
	<table border="1">
		<tbody>
			<tr data-article-reply-id="{$번호}">
				<td>{$번호}</td>
				<td>{$날짜}</td>
				<td>{$작성자}</td>
				<td>{$내용}</td>
				<td><a href="#"
					onclick="if ( confirm('정말 삭제하시겠습니까?') ) { ArticleReply__delete(this); } return false;">삭제</a>
					<a href="#" onclick="return false;">수정</a></td>
			</tr>
		</tbody>
	</table>
</div>


<div class="article-reply-list-box table-box con">
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
			<%--
			<c:forEach items="${articleReplies}" var="articleReply">
				<tr>
					<td>${articleReply.id}</td>
					<td>${articleReply.regDate}</td>
					<td>${articleReply.extra.writer}</td>
					<td>${articleReply.body}</td>
					<td>
						<a href="#" >삭제</a>
						<a href="#">수정</a>
					</td>
				</tr>
			</c:forEach> 
			--%>
		</tbody>
	</table>
</div>

<%@ include file="../part/foot.jspf"%>