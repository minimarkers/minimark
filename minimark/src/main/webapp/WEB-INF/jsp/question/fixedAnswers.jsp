<%@ include file="/WEB-INF/jsp/common.jspf" %>
<h4><spring:message code="Answers" text="?Answers?"/></h4>
<div>
    <a class="action-add" href="#" onclick="return FAEditor.addFixedAnswer();"><spring:message code="AddAnswer" text="?AddAnswer?"/></a>
</div>
<display:table id="curr"  name="question.fixedAnswers" sort="list" pagesize="20" defaultsort="1" defaultorder="ascending" >
    <display:column property="filteredContent"  titleKey="Content"/>
    <display:column property="weight"  titleKey="Weight"/>
    <display:column titleKey="Correct">
        <c:choose>
            <c:when test="${curr.correct}">
                <spring:message code="True" text="?True?"/>
            </c:when>
            <c:otherwise>
                <spring:message code="False" text="?False?"/>
            </c:otherwise>
        </c:choose>
    </display:column>
    <display:column>
        <a class="action-edit" href="#" onclick="return FAEditor.editFixedAnswer(this, ${curr.id}, '${curr.javascriptEscapedContent}', ${curr.weight}, ${curr.correct});"><spring:message code="Edit" text="?Edit?"/></a>
        <a class="action-delete" href="#" onclick="return FAEditor.deleteFixedAnswer(${curr.id});"><spring:message code="Delete" text="?Delete?"/></a>
    </display:column>
</display:table>

<script type="text/javascript">
var FAEditor = {
    inEditing: null,
    inNewEditing: false,
    addedTable: false,
    editorTemplate: new Template('<tr id="faEditor"><td><input id="faEditorId" type="hidden" value="\#{id}"/><textarea id="faEditorContent" class="rowEditing">\#{content}</textarea></td><td><input id="faEditorWeight" type="text" value="\#{weight}"/></td><td><select id="faEditorCorrect"><option value="true"><spring:message code="True" text="?True?" javaScriptEscape="true"/></option><option value="false"><spring:message code="False" text="?False?" javaScriptEscape="true"/></option></select></td><td><a href="#" class="action-confirm" onclick="return FAEditor.saveFixedAnswer();"><spring:message code="Save" text="?Save?" javaScriptEscape="true"/></a>&nbsp;<a href="#" class="action-cancel" onclick="return FAEditor.cancelFixedAnswerEditing();"><spring:message code="Cancel" text="?Cancel?" javaScriptEscape="true"/></a></td></tr>'),
    tableTemplate: new Template('<table id="curr"><thead><tr><th><spring:message code="Content" text="?Content?" javaScriptEscape="true"/></th><th><spring:message code="Weight" text="?Weight?" javaScriptEscape="true"/></th><th><spring:message code="Correct" text="?Correct?" javaScriptEscape="true"/></th><th></th></tr></thead><tbody></tbody></table>'),

    deleteFixedAnswer: function(id) {
        if (confirm('<spring:message code="ConfirmDeleteFixedAnswer" text="?ConfirmDeleteFixedAnswer?" javaScriptEscape="true"/>')) {
            fixedAnswerABo.deleteAnswer(id);
        }
        return false;
    },

    editFixedAnswer: function (element, id, content, weight, correct) {
        this.cancelFixedAnswerEditing();
        elToEdit = element.up(1);
        this.inEditing = elToEdit;
        elToEdit.hide();
        elToEdit.insert({after: this.editorTemplate.evaluate({id: id, content: content, weight: weight, correct: correct})});
        $('faEditorCorrect').value = correct;
        $('faEditorContent').activate();
        return false;
    },

    addFixedAnswer: function() {
      this.cancelFixedAnswerEditing();
      faEditTable = $('curr');
      if (!faEditTable) {
          $('fixedAnswers').insert(this.tableTemplate.evaluate({}));
          this.addedTable = true;
      }
      $('curr').down('tbody').insert(this.editorTemplate.evaluate({}));
      this.inNewEditing = true;
      return false;
    },

    cancelFixedAnswerEditing: function () {
        if (this.inEditing != null) {
            $('faEditor').remove();
            this.inEditing.show();
            this.inEditing = null;
        }
        if (this.inNewEditing) {
            $('faEditor').remove();
            this.inNewEditing = false;
        }
        if (this.addedTable) {
            $('curr').remove();
            this.addedTable = false;
        }
        return false;
    },

    saveFixedAnswer: function () {
        fixedAnswerABo.saveAnswer($('faEditorId').value, $('faEditorContent').value, $('faEditorWeight').value, $('faEditorCorrect').value, $('contentFilter').value);
        this.inEditing = null;
        this.inNewEditing = false;
        this.addedTable = false;
        return false;
    }
};
</script>
