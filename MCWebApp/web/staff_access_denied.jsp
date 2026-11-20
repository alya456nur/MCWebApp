<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Access Denied</title>

        <!-- Bootstrap -->
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">

        <!-- Optional: match your system styling -->
        <link rel="stylesheet" href="assets/css/global.css">
    </head>

    <body class="bg-light">

        <!-- Modal -->
        <div class="modal fade" id="accessDeniedModal" tabindex="-1" aria-hidden="true">
            <div class="modal-dialog modal-dialog-centered">
                <div class="modal-content">

                    <div class="modal-header">
                        <h5 class="modal-title">
                            <%= (request.getAttribute("denyTitle") != null) ? request.getAttribute("denyTitle") : "Access Denied"%>
                        </h5>

                        <!-- Close (X) -->
                        <button type="button" class="btn-close" id="btnCloseX" aria-label="Close"></button>
                    </div>

                    <div class="modal-body">
                        <p class="mb-0">
                            <%= (request.getAttribute("denyMsg") != null) ? request.getAttribute("denyMsg") : "Admin only."%>
                        </p>
                    </div>

                    <div class="modal-footer">
                        <button type="button" class="btn btn-primary" id="btnOk">OK</button>
                    </div>

                </div>
            </div>
        </div>

        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>

        <script>
            (function () {
                const DASHBOARD_URL = "<%= request.getContextPath()%>/StaffDashboard"; // change if your dashboard URL differs

                function goDashboard() {
                    window.location.href = DASHBOARD_URL;
                }

                document.addEventListener("DOMContentLoaded", function () {
                    const modalEl = document.getElementById("accessDeniedModal");

                    // Bootstrap modal instance
                    const modal = new bootstrap.Modal(modalEl, {
                        backdrop: 'static', // cannot close by clicking outside
                        keyboard: false     // cannot close with ESC
                    });

                    // Show immediately
                    modal.show();

                    // Only OK or X will close -> then redirect
                    document.getElementById("btnOk").addEventListener("click", function () {
                        modal.hide();
                    });

                    document.getElementById("btnCloseX").addEventListener("click", function () {
                        modal.hide();
                    });

                    // After modal closes, redirect
                    modalEl.addEventListener("hidden.bs.modal", function () {
                        goDashboard();
                    });
                });
            })();
        </script>

    </body>
</html>
