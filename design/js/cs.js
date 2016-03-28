(function() {
  window.onload = function() {
    var on = function(targets, type, listener) {
      for (var i = 0; i < targets.length; i++){
        targets[i].removeEventListener(type, listener);
        targets[i].addEventListener(type, listener);
      }
    }
    var deleteRow = function(e) {
      var p = e.target.parentNode.parentNode;
      p.parentNode.removeChild(p);
    };
    var recalc = function(target) {
      return function() {
        console.log(target);
      };
    };
    var observe = function() {
      var observed = document.getElementsByClassName('observed');
      on(observed, 'change', recalc("hello!!"));
    };
    var deleteBtns = document.getElementsByClassName('deleteRow');
    on(deleteBtns, 'click', deleteRow);
    var addParts = document.getElementsByClassName('addPart');
    on(addParts, 'click', function(e) {
      var clone = document.querySelector('table.part .hiddenRow').cloneNode(true);
      clone.classList.remove('hiddenRow');
      var target = document.querySelector('table.part > tbody');
      target.appendChild(clone);
      var deleteBtn = clone.querySelectorAll('.deleteRow');
      on(deleteBtn, 'click', deleteRow);
      observe();
    });
    var addItems = document.getElementsByClassName('addItem');
    on(addItems, 'click', function(e) {
      var clone = document.querySelector('table.item .hiddenRow').cloneNode(true);
      clone.classList.remove('hiddenRow');
      var target = document.querySelector('table.item > tbody');
      target.appendChild(clone);
      var deleteBtn = clone.querySelectorAll('.deleteRow');
      on(deleteBtn, 'click', deleteRow);
      observe();
    });
    var addRelations = document.getElementsByClassName('addRelation');
    on(addRelations, 'click', function(e) {
      var clone = document.querySelector('table.relation .hiddenRow').cloneNode(true);
      clone.classList.remove('hiddenRow');
      var target = document.querySelector('table.relation > tbody');
      target.appendChild(clone);
      var deleteBtn = clone.querySelectorAll('.deleteRow');
      on(deleteBtn, 'click', deleteRow);
      observe();
    });
    observe();
  }
})();
