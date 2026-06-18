class SidebarComponent extends HTMLElement {
    constructor() {
        super();
        this.menuOptions = [
            { id: 'solicitudes', label: 'Solicitudes', icon: 'pending_actions', fill: false },
            { id: 'eventos', label: 'Eventos', icon: 'event', fill: false },
            { id: 'minutas', label: 'Minutas', icon: 'description', fill: false },
            { id: 'reportes', label: 'Reportes', icon: 'assessment', fill: false },
        ];
        
        this.bottomOptions = [];
    }

    connectedCallback() {
        this.render();
        window.addEventListener('hashchange', () => this.updateActiveOption());
    }

    render() {
        const currentHash = window.location.hash.slice(1) || 'dashboard';

        this.innerHTML = `
            <aside class="w-[260px] h-screen fixed left-0 top-0 bg-[#1E293B] shadow-md flex flex-col py-6 z-50">
                <!-- Brand logo -->
                <div class="px-6 mb-10 flex items-center gap-3">
                    <div class="w-10 h-10 bg-[#6b46c1] rounded-lg flex items-center justify-center text-[#FFFFFF] font-bold text-2xl">
                        FP
                    </div>
                    <div>
                        <h1 class="text-[#FFFFFF] text-xl font-bold tracking-tight leading-none">FamilyPark</h1>
                        <p class="text-[#d3e4fe]/60 text-[10px] uppercase tracking-widest font-semibold mt-1">Admin ERP</p>
                    </div>
                </div>

                <!-- Main navigation -->
                <nav class="flex-1 space-y-1 overflow-y-auto px-2">
                    ${this.menuOptions.map(opt => this.generateNavLink(opt, currentHash)).join('')}
                </nav>

                <!-- Bottom options -->
                <div class="mt-auto pt-6 border-t border-white/10 space-y-1 px-2">
                    ${this.bottomOptions.map(opt => this.generateNavLink(opt, currentHash)).join('')}
                </div>
            </aside>
        `;
    }

    generateNavLink(opt, currentHash) {
        const isActive = currentHash === opt.id;
        const activeClass = isActive ? 'bg-[#532aa8] text-[#ffffff]' : 'text-[#d3e4fe] hover:text-[#ffffff] hover:bg-white/5';
        const iconFillSetting = opt.fill && isActive ? "font-variation-settings: 'FILL' 1;" : "";
        const customClass = opt.class || '';

        return `
            <a class="flex items-center gap-3 px-4 py-3 transition-all rounded-lg text-sm font-medium ${activeClass} ${customClass}" 
               href="#${opt.id}" data-id="${opt.id}">
                <span class="material-symbols-outlined" style="${iconFillSetting}">${opt.icon}</span>
                <span>${opt.label}</span>
            </a>
        `;
    }

    updateActiveOption() {
        const currentHash = window.location.hash.slice(1) || 'dashboard';
        
        // Update styling of links
        this.querySelectorAll('nav a, div a').forEach(a => {
            const id = a.getAttribute('data-id');
            const opt = [...this.menuOptions, ...this.bottomOptions].find(o => o.id === id);
            if (!opt) return;

            const isActive = currentHash === id;
            
            // Clean classes
            a.classList.remove('bg-[#532aa8]', 'text-[#ffffff]', 'text-[#d3e4fe]', 'hover:text-[#ffffff]', 'hover:bg-white/5');
            
            if (isActive) {
                a.classList.add('bg-[#532aa8]', 'text-[#ffffff]');
                const span = a.querySelector('.material-symbols-outlined');
                if (span) {
                    span.style.fontVariationSettings = opt.fill ? "'FILL' 1" : "";
                }
            } else {
                a.classList.add('text-[#d3e4fe]', 'hover:text-[#ffffff]', 'hover:bg-white/5');
                const span = a.querySelector('.material-symbols-outlined');
                if (span) {
                    span.style.fontVariationSettings = "";
                }
            }
        });
    }
}

customElements.define('sidebar-component', SidebarComponent);
