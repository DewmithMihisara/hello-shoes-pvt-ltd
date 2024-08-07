let cusId;
for (let i = 0; i < 0; i++) {
  $("#tbl-customer-body").append(`
        <tr>
             <td class="text-center">${i}</td>
             <td class="text-center">System Architect</td>
             <td class="text-center">Edinburgh</td>
             <td class="text-center">61</td>
             <td class="text-center">2011-04-25</td>
             <td class="text-center">$320,800</td>
             <td class="text-center">$320,800</td>
             <td class="text-center">$320,800</td>
             <td class="text-center">$320,800</td>
        </tr>
    `);
}

$("#btn-add-customer").on("click", function () {
  $("#btn-save-customer").html(
    '<i class="bi bi-person-fill-add"></i>Add Member'
  );
  $("#customer-modal").modal("show");
});

$("#btn-save-customer").on("click", function () {
  const customer = {
    userEmail: userdetail.username,
    customerId: cusId,
    customerName: $("#customer-name").val(),
    gender: $("#cus-gender").val(),
    contact: $("#cus-contact").val(),
    email: $("#customer-email").val(),
    address: {
      lane: $("#customer-address-lane").val(),
      mainCountry: $("#customer-address-country").val(),
      mainCity: $("#customer-address-city").val(),
      mainState: $("#customer-address-state").val(),
      postalCode: $("#customer-address-code").val(),
    },
    dob: $("#customer-dob").val(),
    totalPoints: 0,
  };

  const requiredFields = [
    "customerName",
    "gender",
    "contact",
    "email",
    "address.lane",
    "address.mainCountry",
    "address.mainCity",
    "address.mainState",
    "address.postalCode",
    "dob",
  ];

  for (const field of requiredFields) {
    let value;
    if (field.includes(".")) {
      const [parentField, childField] = field.split(".");
      value = customer[parentField][childField];
    } else {
      value = customer[field];
    }

    if (!value || value.trim() === "") {
      Swal.fire({
        icon: "warning",
        title: "Incomplete Form",
        text: `Please complete the ${field
          .replace(/([A-Z])/g, " $1")
          .toLowerCase()} field.`,
        showConfirmButton: true,
      });
      return;
    }
  }

  $.ajax({
    url: BASE_URL + "api/v1/customers",
    type: $("#btn-save-customer").text() === "Add Member" ? "POST" : "PUT",
    data: JSON.stringify(customer),
    contentType: "application/json",
    headers: {
      Authorization: "Bearer " + userdetail.jwt,
    },
    success: function (data) {
      const Toast = Swal.mixin({
        toast: true,
        position: "top-end",
        showConfirmButton: false,
        timer: 3000,
        timerProgressBar: true,
        didOpen: (toast) => {
          toast.onmouseenter = Swal.stopTimer;
          toast.onmouseleave = Swal.resumeTimer;
        },
      });
      Toast.fire({
        icon: "success",
        title: data,
      });
      $("#customer-modal").modal("hide");
      loadAllCustomers();
      loadRegeularUserCustomers();
    },
    error: function (error) {
      const Toast = Swal.mixin({
        toast: true,
        position: "top-end",
        showConfirmButton: false,
        timer: 3000,
        timerProgressBar: true,
        didOpen: (toast) => {
          toast.onmouseenter = Swal.stopTimer;
          toast.onmouseleave = Swal.resumeTimer;
        },
      });
      Toast.fire({
        icon: "error",
        title: "Fail Customer Saved !",
      });
    },
  });
});

function loadAllCustomers() {
  $.ajax({
    url: BASE_URL + "api/v1/customers",
    type: "GET",
    headers: {
      Authorization: "Bearer " + userdetail.jwt,
    },
    success: function (data) {
      console.log(data);
      let customers = data;
      let html = "";
      customers.forEach((customer) => {
        html += `
               <tr style="font-size="10px">
               <td class="text-center text-dark bg-light-subtle" style="width:80px;">${
                 customer.customerId
               }</td>
                   <td class="text-center">${customer.customerName}</td>
                   <td class="text-center">${customer.gender}</td>
                   <td class="text-center" style="width:100px;">${
                     customer.dob
                   }</td>
                   <td class="text-center">${customer.contact}</td>
                   <td class="text-center">${customer.email}</td>
                   <td class="text-center">${customer.address.lane},${
          customer.address.mainCountry
        }</td>
                   <td class="text-center">${customer.address.mainCity}</td>
                   <td class="text-center">${customer.address.postalCode}</td>
                   <td class="text-center">${customer.address.mainState}</td>
                   <td class="text-center">${customer.registeredDate}</td>
                   <td class="text-center">${customer.recentPurchaseDate}</td>
                   <td class="text-center">${customer.totalPoints}</td>
                   <td class="text-center">${customer.level}</td>
                   ${
                     userdetail.role !== "USER"
                       ? `<td class="text-center " >
                           <div class="d-flex" style="width:80px;">
                             <button class="btn btn-sm btn-light btn-edit-customer"><i class="bi bi-pen-fill"></i>Edit</button>
                             <button class="btn btn-sm btn-danger ms-2 btn-delete-customer"><i class="bi bi-trash3-fill"></i></button>
                           </div>
                         </td>`
                       : `<td class="text-center " >
                       <div class="d-flex" style="width:80px;">
                        </div>
                     </td>`
                   }
            </tr>
                `;
      });

      $("#tbl-customer-body").html(html);

      if (userdetail.role !== "USER") {
        $(".btn-edit-customer").on("click", function () {
          $("#btn-save-customer").html(
            '<i class="bi bi-person-fill-add"></i>Update Member'
          );
          $("#customer-modal").modal("show");

          let id = $(this).closest("tr").find("td:first-child").text();
          cusId = id;

          renderCustomer(id);
        });

        $(".btn-delete-customer").on("click", function () {
          let id = $(this).closest("tr").find("td:first-child").text();
          Swal.fire({
            title: "Are you sure?",
            text: "You won't be able to revert this!",
            icon: "warning",
            showCancelButton: true,
            confirmButtonColor: "#3085d6",
            cancelButtonColor: "#d33",
            confirmButtonText: "Yes, delete Customer!",
          }).then((result) => {
            if (result.isConfirmed) {
              $.ajax({
                url: BASE_URL + "api/v1/customers/" + id,
                type: "DELETE",
                headers: {
                  Authorization: "Bearer " + userdetail.jwt,
                },
                success: function (data) {
                  Swal.fire(
                    "Deleted!",
                    "Customer has been deleted.",
                    "success"
                  );
                  loadAllCustomers();
                },
                error: function (error) {
                  Swal.fire(
                    "Failed!",
                    "Customer has not been deleted.",
                    "error"
                  );
                },
              });
            }
          });
        });
      }
    },
    error: function (error) {
      const Toast = Swal.mixin({
        toast: true,
        position: "top-end",
        showConfirmButton: false,
        timer: 3000,
        timerProgressBar: true,
        didOpen: (toast) => {
          toast.onmouseenter = Swal.stopTimer;
          toast.onmouseleave = Swal.resumeTimer;
        },
      });
      Toast.fire({
        icon: "error",
        title: "session expired",
      });
    },
  });
}

loadAllCustomers();

function renderCustomer(id) {
  $.ajax({
    type: "GET",
    url: BASE_URL + "api/v1/customers/" + id,
    headers: {
      Authorization: "Bearer " + userdetail.jwt,
    },
    success: function (data) {
      $("#customer-name").val(data.customerName);
      $("#cus-gender").val(data.gender);
      $("#cus-contact").val(data.contact);
      $("#customer-email").val(data.email);
      $("#customer-dob").val(data.dob);
      $("#customer-address-lane").val(data.address.lane);
      $("#customer-address-city").val(data.address.mainCity);
      $("#customer-address-state").val(data.address.mainState);
      $("#customer-address-code").val(data.address.postalCode);
      $("#customer-address-country").val(data.address.mainCountry);
    },
    error: function (error) {
      alert("Supplier not found !");
    },
  });
}

$("#customer-clear").on("click", function () {
  $("#customer-name").val("");
  $("#cus-gender").val("");
  $("#cus-contact").val("");
  $("#customer-email").val("");
  $("#customer-dob").val("");
  $("#customer-address-lane").val("");
  $("#customer-address-city").val("");
  $("#customer-address-state").val("");
  $("#customer-address-code").val("");
  $("#customer-address-country").val("");
});

function loadRegeularUserCustomers() {
  $.ajax({
    url: BASE_URL + "api/v1/customers",
    type: "GET",
    headers: {
      Authorization: "Bearer " + userdetail.jwt,
    },
    success: function (data) {
      console.log(data);

      let customers = data;
      let html = "";
      customers.forEach((customer) => {
        html += `
               <tr style="font-size:10px;">
               <td class="text-center text-dark bg-light-subtle" style="width:80px;">${customer.customerId}</td>
               <td class="text-center">${customer.customerName}</td>
               <td class="text-center">${customer.gender}</td>
               <td class="text-center" style="width:100px;">${customer.dob}</td>
               <td class="text-center">${customer.contact}</td>
               <td class="text-center">${customer.email}</td>
               <td class="text-center">${customer.address.lane},${customer.address.mainCountry}</td>
               <td class="text-center">${customer.address.mainCity}</td>
               <td class="text-center">${customer.address.postalCode}</td>
               <td class="text-center">${customer.address.mainState}</td>
               <td class="text-center">${customer.registeredDate}</td>
               <td class="text-center">${customer.recentPurchaseDate}</td>
               <td class="text-center">${customer.totalPoints}</td>
               <td class="text-center">${customer.level}</td>
            </tr>
                `;
      });

      $("#tbl-customer-regeular-body").html(html);
      $(document).ready(function () {
        $("#tbl-customer-regeular").DataTable({
          language: {
            search: "Search Supplier:",
            lengthMenu: "Display count _MENU_",
            info: "Showing _START_ to _END_ of _TOTAL_ records",
            infoEmpty: "Showing 0 to 0 of 0 records",
            infoFiltered: "(filtered from _MAX_ total records)",
          },
        });
      });
    },
    error: function (error) {
      const Toast = Swal.mixin({
        toast: true,
        position: "top-end",
        showConfirmButton: false,
        timer: 3000,
        timerProgressBar: true,
        didOpen: (toast) => {
          toast.onmouseenter = Swal.stopTimer;
          toast.onmouseleave = Swal.resumeTimer;
        },
      });
      Toast.fire({
        icon: "error",
        title: "session expired",
      });
    },
  });
}

loadRegeularUserCustomers();
