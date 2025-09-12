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
