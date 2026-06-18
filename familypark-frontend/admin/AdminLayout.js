class AdminLayout extends HTMLElement {
    connectedCallback() {
        this.render();
    }

    render() {
        this.innerHTML = `
            <div class="flex min-h-screen bg-[#F8FAFC]">
                <!-- Sidebar -->
                <sidebar-component></sidebar-component>
                
                <!-- Main Area -->
                <div class="flex-1 flex flex-col pl-[260px]">
                    <!-- Navbar -->
                    <navbar-component id="global-navbar" title="Dashboard"></navbar-component>
                    
                    <!-- Content Main Area -->
                    <main id="main-content" class="pt-16 min-h-screen overflow-y-auto">
                        <div class="p-8 max-w-[1440px] mx-auto">
                            <!-- Dynamically loaded content goes here -->
                        </div>
                    </main>
                </div>
            </div>
        `;
    }
}

customElements.define('admin-layout', AdminLayout);
