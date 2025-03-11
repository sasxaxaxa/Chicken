document.getElementById("moveForm").addEventListener("submit", function (event) {
    event.preventDefault();

    const name = document.getElementById("name").value;
    const x = document.getElementById("x").value;
    const y = document.getElementById("y").value;

    if (!name || !x || !y) {
        alert("Заполните все поля");
        return;
    }

    const url = `http://localhost:8080/character/${name}/move?x=${x}&y=${y}`;
    console.log("Отправляемый токен:", localStorage.getItem("token"));

    fetch(url, {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
            "Authorization": "Bearer " + localStorage.getItem("token") // ✅ Добавлен пробел
        }
    })
        .then(response => {
            if (response.ok) {
                return response.text(); // Получаем текстовый ответ от сервера
            } else {
                throw new Error(`Ошибка при перемещении персонажа. Код ошибки: ${response.status}`);
            }
        })
        .then(data => {
            document.getElementById("response").textContent = `Персонаж ${name} перемещён на координаты (${x}, ${y}).`;
            document.getElementById("response").style.color = "green";
        })
        .catch(error => {
            document.getElementById("response").textContent = error.message;
            document.getElementById("response").style.color = "red";
        });
});

document.getElementById("gatherForm").addEventListener("submit", function (event) {
    event.preventDefault();

    const name = document.getElementById("gatherName").value;

    if (!name) {
        alert("Введите имя персонажа");
        return;
    }

    const url = `http://localhost:8080/character/${name}/gathering`

    fetch(url, {
        method: "Post",
    })
        .then(response => {
            if (response.ok) {
                return response.text();
            } else {
                throw new Error(`Ошибка при добычи ресурса. Код ошибки: ${response.status}}`)
            }
        })
        .then(data => {
            document.getElementById("response").textContent = `Персонаж ${name} добыл ресурс.`;
            document.getElementById("response").style.color = "green";
        })
        .catch(error => {
            document.getElementById("response").textContent = error.message;
            document.getElementById("response").style.color = "red";
        });
});

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
            .then(response => response.json())
            .then(data => {
                console.log("Ответ сервера:", data); // Лог ответа
                document.getElementById("response").textContent = JSON.stringify(data);
            })
            .catch(error => {
                console.error("Ошибка запроса:", error);
                document.getElementById("response").textContent = "Ошибка запроса";
            });
    }
})