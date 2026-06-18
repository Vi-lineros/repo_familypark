class NavbarComponent extends HTMLElement {
    static get observedAttributes() {
        return ['title'];
    }

    constructor() {
        super();
        this.titleText = 'Dashboard';
    }

    attributeChangedCallback(name, oldValue, newValue) {
        if (name === 'title') {
            this.titleText = newValue;
            const titleEl = this.querySelector('.navbar-title');
            if (titleEl) {
                titleEl.textContent = newValue;
            }
        }
    }

    connectedCallback() {
        this.render();
    }

    render() {
        this.innerHTML = `
            <header class="h-16 fixed top-0 right-0 flex justify-between items-center px-8 w-[calc(100%-260px)] z-40 bg-[#FFFFFF] border-b border-[#cbc3d5] shadow-sm">
                <!-- Search and Title -->
                <div class="flex items-center gap-6">
                    <div class="flex items-center gap-4 mr-4">
                        <h1 class="navbar-title font-semibold text-lg text-[#0b1c30] whitespace-nowrap">${this.titleText}</h1>
                        <div class="h-6 w-px bg-[#cbc3d5]"></div>
                    </div>
                    <div class="relative w-96">
                        <span class="material-symbols-outlined absolute left-3 top-1/2 -translate-y-1/2 text-[#494453]">search</span>
                        <input class="w-full bg-[#eff4ff] border-none rounded-full py-2 pl-10 pr-4 text-sm focus:ring-2 focus:ring-[#532aa8]/30 transition-all text-[#0b1c30] placeholder-[#494453]/60" 
                               placeholder="Buscar eventos, clientes o personal..." type="text">
                    </div>
                </div>

                <!-- User actions and profile -->
                <div class="flex items-center gap-4">
                    <button class="hover:bg-[#eff4ff] rounded-full p-2 text-[#494453] transition-colors flex items-center justify-center">
                        <span class="material-symbols-outlined">notifications</span>
                    </button>
                    <button class="hover:bg-[#eff4ff] rounded-full p-2 text-[#494453] transition-colors flex items-center justify-center">
                        <span class="material-symbols-outlined">apps</span>
                    </button>
                    <button class="hover:bg-[#eff4ff] rounded-full p-2 text-[#494453] transition-colors flex items-center justify-center">
                        <span class="material-symbols-outlined">help_outline</span>
                    </button>
                    
                    <div class="h-8 w-px bg-[#cbc3d5] mx-1"></div>
                    
                    <div class="flex items-center gap-3 pl-2">
                        <div class="text-right hidden xl:block">
                            <p class="font-semibold text-sm text-[#0b1c30] leading-tight">Admin User</p>
                            <p class="text-[11px] text-[#494453] font-medium">Super Administrator</p>
                        </div>
                        <img alt="User Avatar" class="w-10 h-10 rounded-full border border-[#cbc3d5] object-cover" 
                             src="https://lh3.googleusercontent.com/aida-public/AB6AXuD53COp1WFg5uSNvlmbeL-NQuAgRecDp2dHtFRyJ8O4I2uGFddZmAOBLKEiN-xjSN7dLA3jR6Ukw2zIi5hCALJfAv1rvWuaCFuEP6QwxzKZlePX8ralr02Hb5jB6LsS8W8yCPWphhsR2mcvxPsjs12_r9_iGpAD0_6loC-u5TgXZIydg5wb9Q5nH24Z9AIB3HShD9P4gnnW23fca7aRfq6lMY40vRuYmqxlu_ex5fxcW7eec4QJHQtfJliIkqQ3MNkIiDjvxnqO_WWc">
                    </div>
                </div>
            </header>
        `;
    }
}

customElements.define('navbar-component', NavbarComponent);
