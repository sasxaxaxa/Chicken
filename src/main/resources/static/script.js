document.addEventListener("DOMContentLoaded", function () {
    const tokenInput = document.getElementById("token");
    const saveTokenBtn = document.getElementById("saveToken");

    if (localStorage.getItem("token")) {
        tokenInput.value = localStorage.getItem("token");
    }

    saveTokenBtn.addEventListener("click", function () {
        const token = tokenInput.value.trim();
        if (token) {
            localStorage.setItem("token", token);
            alert("Токен сохранен!");
        } else {
            alert("Введите токен.")
        }
    });

    function getToken() {
        return localStorage.getItem("token") || "";
    }

    function sendRequest(url, data) {
        const token = localStorage.getItem("token");
        console.log("Отправляемый токен:", token); // Лог для проверки

        if (!token) {
            alert("Введите токен!");
            return;
        }

        return fetch(url, {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
                "Authorization": "Bearer " + token
            },
            body: JSON.stringify(data)
        })
            .then(response => {
                if (!response.ok) {
                    throw new Error(`Ошибка запроса. Код ошибки: ${response.status}`)
                }
                return response.text();
            })
            .then(data => {
                document.getElementById("response").textContent = data;
                document.getElementById("response").style.color = "green";
            })
            .catch(error => {
                document.getElementById("response").textContent = error.message;
                document.getElementById("response").style.color = "red";
            })
    }

    function moveCharacter() {
        const name = document.getElementById("name").value;
        const x = document.getElementById("x").value;
        const y = document.getElementById("y").value;

        if (!name || !x || !y) {
            alert("Заполните все поля")
            return;
        }

        const url = `http://localhost:8080/character/${name}/move?x=${x}&y=${y}`;
        sendRequest(url,)
    }

    function gatherResources() {
        const name = document.getElementById("gatherName").value;
        const count = parseInt(document.getElementById("count").value) || 1;

        if (!name) {
            alert("Введите имя персонажа");
            return;
        }
        const url = `http://localhost:8080/character/${name}/gather`
        const data = {count: count}
        sendRequest(url, data)
    }

    document.getElementById("moveForm").addEventListener("submit", function (event) {
        event.preventDefault();
        moveCharacter();
    });

    document.getElementById("gatherForm").addEventListener("submit", function (event) {
        event.preventDefault();
        gatherResources();
    })

})

