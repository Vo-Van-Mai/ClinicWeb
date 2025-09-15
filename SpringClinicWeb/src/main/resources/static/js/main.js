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
