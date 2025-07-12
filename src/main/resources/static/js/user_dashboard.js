
  const sidebar = document.getElementById('navbarToggleExternalContent');
//   const mainContent = document.getElementById('content');

  sidebar.addEventListener('shown.bs.collapse', () => {
    document.body.classList.add('sidebar-shown');
    console.log("toggle open")
  });

  sidebar.addEventListener('hidden.bs.collapse', () => {
    document.body.classList.remove('sidebar-shown');
    console.log("toggle click")
  });

// for delete contact
  function deleteContact(element) {
            const contactId = element.getAttribute("data-id");
            const csrfToken = document.getElementById("csrfToken").value;

            console.log("enter in delete feature " + contactId)
            if (!confirm("Are you sure you want to delete this contact?")) return;

            fetch(`/user/delete?ref=${contactId}`, {
                method: 'DELETE',
                headers: {
                    'X-CSRF-TOKEN': csrfToken
                }
            })
            .then(response => {
                if (response.ok) {
                    alert("Contact deleted successfully.");
                    // location.reload();
                    // Remove the card from DOM
                    element.closest('.col-auto').remove();
                    
                } else {
                    alert("Failed to delete contact.");
                }
            })
            .catch(error => {
                console.error("Error deleting contact:", error);
                alert("Something went wrong.");
            });
            console.log("exit delete feature")
        }
