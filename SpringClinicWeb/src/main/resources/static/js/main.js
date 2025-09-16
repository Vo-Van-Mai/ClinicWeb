function deleteSpecialize(endpoint, id) {
    if (confirm("Bạn muốn xóa khoa này!") === true) {
        fetch(endpoint + id, {method: 'DELETE'})
                .then(res => res.text().then(text => ({ok: res.ok, text})))
                .then(({ ok, text }) => {
                    if (!ok)
                        alert("Lỗi: " + text);
                    else {
                        alert(text);
                        location.reload();
                    }
                })
                .catch(err => alert("Lỗi mạng: " + err.message));
    }
}


function deleteService(endpoint, id) {
    if (confirm("Bạn muốn xóa dịch vụ này!") === true) {
        fetch(endpoint + id, {method: 'DELETE'})
                .then(res => res.text().then(text => ({ok: res.ok, text})))
                .then(({ ok, text }) => {
                    if (!ok)
                        alert("Lỗi: " + text);
                    else {
                        alert(text);
                        location.reload();
                    }
                })
                .catch(err => alert("Lỗi mạng: " + err.message));
    }
}

function deleteMedicine(endpoint) {
    if (confirm("Ban co chac muon xoa")===true) {
        fetch(endpoint, {
            method: "delete"
        }).then(res => {
            if (res.status === 204)
                location.reload();
            else
                alert("He thong co loi!");
        });
    }
}

function handleVerified(endpoint, action) {
    let msg = action === '/verified' ? "Bạn muốn xác nhận bác sĩ này?" : "Bạn muốn hủy quyền bác sĩ này?";

    if (confirm(msg)) {
        fetch(endpoint + action, {method: 'POST'})
                .then(res => res.text().then(text => ({ok: res.ok, text})))
                .then(({ ok, text }) => {
                    if (!ok) {
                        alert("Lỗi: " + text);
                    } else {
                        alert("Thực hiện thành công!");
                        location.reload();
                }
                })
                .catch(err => alert("Lỗi mạng: " + err.message));
    }
}



