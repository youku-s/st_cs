(function() {
  window.onload = function() {
    var on = function(targets, type, listener) {
      for (var i = 0; i < targets.length; i++){
        targets[i].addEventListener(type, listener);
      }
    }
    var deleteRow = function(e) {
      var p = e.target.parentNode.parentNode;
      p.parentNode.removeChild(p);
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
    });
  }
})();
