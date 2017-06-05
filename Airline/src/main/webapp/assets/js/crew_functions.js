function move(row) {
	var maxPilots = 2, maxNavigators = 1, maxRadiomans = 1, maxStewardesses = 4;
	crewTable = document.getElementById("crewTable");
	usersTable = document.getElementById("usersTable");
	usersBody = document.getElementById("usersTableBody");
	crewBody = document.getElementById("crewTableBody");
	
	if (isDescendant(usersTable, row)) {
		currentRole = row.getElementsByTagName("TD")[2].innerHTML;
		rows = crewTable.getElementsByTagName("TR");
		count = 0;
		for (i = 1; i < rows.length; i++) {
			role = rows[i].getElementsByTagName("TD")[2].innerHTML;
			if(currentRole == role){
				count++;
			}
		}
		
		switch (currentRole) {
		case 'pilot':
			if(count == maxPilots) return;
			break;
		case 'navigator':
			if(count == maxNavigators) return;
			break;
		case 'radioman':
			if(count == maxRadiomans) return;
			break;
		case 'stewardess':
			if(count == maxStewardesses) return;
			break;
		default:
		}
		crewBody.append(row).remove();
	} else {
		usersBody.append(row).remove();
	}
}

function isDescendant(parent, child) {
	var node = child.parentNode;
	while (node != null) {
		if (node == parent) {
			return true;
		}
		node = node.parentNode;
	}
	return false;
}

function sortTable(n) {
	var table, rows, switching, i, x, y, shouldSwitch, dir, switchcount = 0;
	table = document.getElementById("usersTable");
	switching = true;
	dir = "asc";
	while (switching) {
		switching = false;
		rows = table.getElementsByTagName("TR");
		for (i = 1; i < (rows.length - 1); i++) {
			shouldSwitch = false;
			x = rows[i].getElementsByTagName("TD")[n];
			y = rows[i + 1].getElementsByTagName("TD")[n];
			if (dir == "asc") {
				if (x.innerHTML.toLowerCase() > y.innerHTML.toLowerCase()) {
					shouldSwitch = true;
					break;
				}
			} else if (dir == "desc") {
				if (x.innerHTML.toLowerCase() < y.innerHTML.toLowerCase()) {
					shouldSwitch = true;
					break;
				}
			}
		}
		if (shouldSwitch) {
			rows[i].parentNode.insertBefore(rows[i + 1], rows[i]);
			switching = true;
			switchcount++;
		} else {
			if (switchcount == 0 && dir == "asc") {
				dir = "desc";
				switching = true;
			}
		}
	}
}

function filterRole() {
	var input, filter, table, tr, td, i;
	input = document.getElementById("role");
	filter = input.value;
	if (filter == 'Role') {
		filter = '';
	}
	table = document.getElementById("usersTable");
	tr = table.getElementsByTagName("tr");
	for (i = 0; i < tr.length; i++) {
		td = tr[i].getElementsByTagName("td")[2];
		if (td) {
			if (td.innerHTML.indexOf(filter) > -1) {
				tr[i].style.display = "";
			} else {
				tr[i].style.display = "none";
			}
		}
	}
}
function toLink(link){
	window.location.href = link;
}