function Parancoe() {
    function Util() {
        this.initDWR = function () {
            useLoadingMessage();
            dwr.engine.reverseAjax = true;
        };

        this.disableFormElement = function (elementId) {
            $(elementId).disable();
        }

        this.enableFormElement = function (elementId) {
            $(elementId).enable();
        }

        this.readOnlyFormElement = function (elementId) {
            $(elementId).readOnly = true;
        }

        this.writeFormElement = function (elementId) {
            $(elementId).readOnly = false;
        }

        this.fullDisableFormElement = function (elementId) {
            this.readOnlyFormElement(elementId);
            this.disableFormElement(elementId);
        }

        this.fullEnableFormElement = function (elementId) {
            this.enableFormElement(elementId);
            this.writeFormElement(elementId);
        }

        this.hideRow = function (tableId, rowNum) {
            $$('#'+tableId+' tr')[rowNum].hide();
        }

        this.focusFirstElementInPage = function () {
            var forms = $A($$('form:first-of-type'));

            if (forms.length > 0) {
                forms[0].focusFirstElement();
            }
        }

    }

    this.util = new Util();
}

var parancoe = new Parancoe();

function useLoadingMessage(message) {
    var loadingMessage;
    if (message) loadingMessage = message;
    else loadingMessage = "Loading...";

    dwr.engine.setPreHook(function() {
        var messageZone = $('messageZone');
        if (!messageZone) {
            messageZone = document.createElement('div');
            messageZone.setAttribute('id', 'messageZone');

            document.body.appendChild(messageZone);
            var text = document.createTextNode(loadingMessage);
            messageZone.appendChild(text);
        }
        else {
            messageZone.innerHTML = loadingMessage;
            messageZone.style.visibility = 'visible';
        }
    });

    dwr.engine.setPostHook(function() {
        $('messageZone').style.visibility = 'hidden';
    });
}
