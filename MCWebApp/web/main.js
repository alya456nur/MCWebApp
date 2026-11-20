document.addEventListener("DOMContentLoaded", () => {

    // =============================
    // Helpers
    // =============================
    function getContextPath() {
        const path = window.location.pathname;
        const index = path.indexOf("/", 1);
        return index > 0 ? path.substring(0, index) : "";
    }

    function formatRM(num) {
        const n = Number(num || 0);
        return "RM" + n.toFixed(2);
    }

    // =============================
    // ✅ Bootstrap Message Modal Helper
    // =============================
    function showMsgModal(title, message, onClose) {
        const modalEl = document.getElementById("msgModal");
        const titleEl = document.getElementById("msgModalTitle");
        const bodyEl = document.getElementById("msgModalBody");

        // fallback if modal not found (avoid breaking)
        if (!modalEl || !titleEl || !bodyEl || typeof bootstrap === "undefined") {
            alert((title ? title + "\n" : "") + message);
            if (typeof onClose === "function")
                onClose();
            return;
        }

        titleEl.textContent = title || "Message";
        bodyEl.textContent = message || "";

        const modal = new bootstrap.Modal(modalEl, {
            backdrop: "static",
            keyboard: false
        });

        // Run callback after modal closes (only once)
        const handler = () => {
            modalEl.removeEventListener("hidden.bs.modal", handler);
            if (typeof onClose === "function")
                onClose();
        };

        modalEl.addEventListener("hidden.bs.modal", handler);
        modal.show();
    }
    
    
    // =============================
    // ✅ Servlet Call: Add To Cart
    // =============================
    async function addToCartServlet(varId, qty) {
        try {
            const res = await fetch(getContextPath() + "/AddToCart", {
                method: "POST",
                headers: {"Content-Type": "application/x-www-form-urlencoded"},
                body: new URLSearchParams({varId, qty})
            });

            // servlet returns JSON always
            let data = {};
            try {
                data = await res.json();
            } catch (e) {
                // if something went wrong and not JSON
                const text = await res.text().catch(() => "");
                data.message = text || "Unknown response.";
            }

            // 401 => login
            if (res.status === 401) {
                showMsgModal("Login Required", data.message || "Please login first.", () => {
                    window.location.href = getContextPath() + "/login.jsp";
                });
                return;
            }

            // ok => success
            if (res.ok && (data.message || "").trim() === "OK") {
                showMsgModal("Added to Cart", "✅ Item has been added to your cart!");
                return;
            }

            // other errors
            showMsgModal("Failed", "❌ " + (data.message || "Failed to add to cart."));
        } catch (err) {
            console.error(err);
            showMsgModal("Failed", "❌ Failed to add to cart.");
        }
    }

    // =============================
    // ✅ Bind Buttons (CustDashboard)
    // =============================

    // Eyes: button has data-variant-id
    document.querySelectorAll(".addCart[data-variant-id]").forEach(btn => {
        btn.addEventListener("click", (e) => {
            e.preventDefault();
            const varId = btn.dataset.variantId;
            if (!varId) {
                showMsgModal("Error", "Missing varId.");
                return;
            }
            addToCartServlet(varId, 1);
        });
    });

    // Lips: read <select class="variantSelect"> in same card
    document.querySelectorAll(".addCartLips").forEach(btn => {
        btn.addEventListener("click", (e) => {
            e.preventDefault();

            const card = btn.closest(".card");
            const select = card ? card.querySelector("select.variantSelect") : null;

            if (!select || !select.value) {
                showMsgModal("Select Variation", "Please select a variation first.");
                return;
            }

            addToCartServlet(select.value, 1);
        });
    });


    // =============================
    // ✅ Cart Selection Logic
    // =============================
    const checkboxes = document.querySelectorAll(".select-item");
    const totalEl = document.getElementById("total");
    const checkoutBtn = document.getElementById("checkoutBtn");

    const modalTotal = document.getElementById("modalTotal");
    const hiddenContainer = document.getElementById("selectedHiddenInputs");
    const paymentModal = document.getElementById("paymentModal");

    function getSelectedItems() {
        const selected = [];
        checkboxes.forEach(cb => {
            if (cb.checked) {
                selected.push({
                    orderVarId: cb.dataset.ordervarId,
                    price: Number(cb.dataset.price || 0)
                });
            }
        });
        return selected;
    }

    function updateTotalAndButton() {
        const selected = getSelectedItems();
        const sum = selected.reduce((acc, it) => acc + it.price, 0);

        if (totalEl)
            totalEl.textContent = "Total: " + formatRM(sum);

        // ✅ enable only if has selection
        if (checkoutBtn)
            checkoutBtn.disabled = selected.length === 0;

        // Update modal value too (when ticking without opening modal)
        if (modalTotal)
            modalTotal.value = formatRM(sum);
    }

    // When checkbox changes
    checkboxes.forEach(cb => cb.addEventListener("change", updateTotalAndButton));

    // Before modal opens, inject hidden inputs (orderVarIds[])
    if (paymentModal) {
        paymentModal.addEventListener("show.bs.modal", () => {
            const selected = getSelectedItems();
            const sum = selected.reduce((acc, it) => acc + it.price, 0);

            if (modalTotal)
                modalTotal.value = formatRM(sum);

            // Clear old hidden inputs
            if (hiddenContainer)
                hiddenContainer.innerHTML = "";

            // Add one hidden input per selected item
            selected.forEach(it => {
                const input = document.createElement("input");
                input.type = "hidden";
                input.name = "orderVarIds";
                input.value = it.orderVarId;
                hiddenContainer.appendChild(input);
            });

            // Safety: if no selected, prevent modal
            if (selected.length === 0) {
                showMsgModal("No Items Selected", "Please select at least 1 item to checkout.", () => {
                    const modalInstance = bootstrap.Modal.getInstance(paymentModal);
                    if (modalInstance)
                        modalInstance.hide();
                });
            }
        });
    }

    // Init total on load
    updateTotalAndButton();

    document.querySelectorAll(".variation-select").forEach(select => {
        select.addEventListener("change", function () {
            const selectedId = this.value;
            const container = this.closest(".variation-container");
            const items = container.querySelectorAll(".variation-item");

            items.forEach(item => {
                item.style.display =
                        item.dataset.varId === selectedId ? "block" : "none";
            });
        });
    });

});


// -----------------------------
// Your existing functions
// -----------------------------
function loadContent(page) {
    fetch(page)
            .then(res => res.text())
            .then(html => {
                const contentEl = document.getElementById("content");
                if (!contentEl)
                    return;

                contentEl.innerHTML = html;

                if (document.getElementById("myChart") && typeof renderChart === "function") {
                    renderChart();
                }
            })
            .catch(() => {
                const contentEl = document.getElementById("content");
                if (!contentEl)
                    return;
                contentEl.innerHTML = "<h2>Error loading page.</h2>";
            });
}

function logout() {
    sessionStorage.clear();
    localStorage.clear();

    const path = window.location.pathname;
    const index = path.indexOf("/", 1);
    const ctx = index > 0 ? path.substring(0, index) : "";

    window.location.href = ctx + "/Logout";
}
