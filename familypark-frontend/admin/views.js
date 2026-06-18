// Views Module for Admin ERP Views templates
const Views = {
    // ----------------------------------------------------
    // 1. Dashboard View async function renderSolicitudes()
    // ----------------------------------------------------
    dashboard: `
        <div>
            <!-- Breadcrumbs & Header -->
            <div class="flex flex-col md:flex-row md:items-end justify-between gap-6 mb-10">
                <div>
                    <nav class="flex items-center gap-2 text-sm text-[#494453] mb-2">
                        <span>Admin</span>
                        <span class="material-symbols-outlined text-[16px]">chevron_right</span>
                        <span class="text-[#532aa8] font-semibold">Dashboard</span>
                    </nav>
                    <h2 class="text-3xl font-bold text-[#0b1c30]">Dashboard General</h2>
                    <p class="text-[#494453] mt-1">Visión general del estado operativo de FamilyPark.</p>
                </div>
            </div>

            <!-- Bento Stats Grid -->
            <div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-6 mb-10">
                <div class="glass-card p-6 rounded-xl flex flex-col justify-between">
                    <div class="flex justify-between items-start mb-4">
                        <div class="p-2 bg-[#6b46c1]/10 text-[#532aa8] rounded-lg">
                            <span class="material-symbols-outlined">event_available</span>
                        </div>
                        <span class="text-[#10B981] font-bold text-xs">+12%</span>
                    </div>
                    <div>
                        <p class="text-[#494453] text-sm font-medium">Reservas del Mes</p>
                        <h3 class="text-3xl font-bold mt-1 text-[#0b1c30]">142</h3>
                    </div>
                </div>

                <div class="glass-card p-6 rounded-xl flex flex-col justify-between">
                    <div class="flex justify-between items-start mb-4">
                        <div class="p-2 bg-[#3B82F6]/10 text-[#3B82F6] rounded-lg">
                            <span class="material-symbols-outlined">pending_actions</span>
                        </div>
                        <span class="text-[#EF4444] font-bold text-xs">-2%</span>
                    </div>
                    <div>
                        <p class="text-[#494453] text-sm font-medium">Solicitudes Pendientes</p>
                        <h3 class="text-3xl font-bold mt-1 text-[#0b1c30]">08</h3>
                    </div>
                </div>

                <div class="glass-card p-6 rounded-xl flex flex-col justify-between">
                    <div class="flex justify-between items-start mb-4">
                        <div class="p-2 bg-[#10B981]/10 text-[#10B981] rounded-lg">
                            <span class="material-symbols-outlined">check_circle</span>
                        </div>
                        <span class="text-[#10B981] font-bold text-xs">+5%</span>
                    </div>
                    <div>
                        <p class="text-[#494453] text-sm font-medium">Eventos Completados</p>
                        <h3 class="text-3xl font-bold mt-1 text-[#0b1c30]">1,248</h3>
                    </div>
                </div>

                <div class="glass-card p-6 rounded-xl bg-[#1E293B] relative overflow-hidden group">
                    <div class="absolute inset-0 opacity-10 pointer-events-none">
                        <div class="grid grid-cols-6 h-full w-full">
                            <div class="border-r border-white/20 border-b"></div>
                            <div class="border-r border-white/20 border-b"></div>
                            <div class="border-r border-white/20 border-b"></div>
                            <div class="border-r border-white/20 border-b"></div>
                            <div class="border-r border-white/20 border-b"></div>
                            <div class="border-b border-white/20"></div>
                        </div>
                    </div>
                    <div class="relative z-10">
                        <p class="text-[#d3e4fe]/80 text-sm font-medium">Ocupación Promedio</p>
                        <h3 class="text-white text-3xl font-bold mt-1">87%</h3>
                        <div class="mt-4 w-full bg-white/20 h-2 rounded-full overflow-hidden">
                            <div class="bg-[#d0bcff] h-full w-[87%] rounded-full shadow-[0_0_10px_#d0bcff]"></div>
                        </div>
                    </div>
                </div>
            </div>
            
            <!-- Welcome Info Graph Card Placeholder -->
            <div class="glass-card p-8 rounded-2xl mb-10">
                <h4 class="font-bold text-lg text-[#0b1c30] mb-4">Rendimiento Semanal</h4>
                <div class="h-64 bg-[#eff4ff]/50 rounded-xl flex items-center justify-center border border-dashed border-[#cbc3d5]">
                    <div class="text-center">
                        <span class="material-symbols-outlined text-4xl text-[#532aa8]">bar_chart</span>
                        <p class="text-[#494453] mt-2 font-medium">Gráfico de transacciones y flujo de visitas</p>
                    </div>
                </div>
            </div>
        </div>
    `,

    // ----------------------------------------------------
    // 2. Solicitudes View
    // ----------------------------------------------------
    solicitudes: `
        <div>
            <!-- Breadcrumbs & Header -->
            <div class="flex flex-col md:flex-row md:items-end justify-between gap-6 mb-10">
                <div>
                    <nav class="flex items-center gap-2 text-sm text-[#494453] mb-2">
                        <span>Dashboard</span>
                        <span class="material-symbols-outlined text-[16px]">chevron_right</span>
                        <span class="text-[#532aa8] font-semibold">Solicitudes</span>
                    </nav>
                    <h2 class="text-3xl font-bold text-[#0b1c30]">Solicitudes de Clientes</h2>
                    <p class="text-[#494453] mt-1">Gestión y aprobación de solicitudes para reservas de eventos.</p>
                </div>
                <button id="refresh-solicitudes" class="flex items-center gap-2 bg-[#532aa8] text-white px-6 py-2.5 rounded-xl font-semibold hover:opacity-90 shadow-lg shadow-[#532aa8]/20 transition-all">
                    <span class="material-symbols-outlined">refresh</span>
                    Actualizar
                </button>
            </div>

            <!-- Summary cards -->
            <div class="grid grid-cols-1 md:grid-cols-4 gap-6 mb-10">
                <div class="p-6 rounded-2xl border border-[#cbc3d5] bg-white">
                    <p class="text-sm text-[#494453]">Total solicitudes</p>
                    <h3 id="summary-total-solicitudes" class="text-3xl font-bold mt-3 text-[#0b1c30]">0</h3>
                </div>
                <div class="p-6 rounded-2xl border border-[#cbc3d5] bg-white">
                    <p class="text-sm text-[#494453]">Pendientes</p>
                    <h3 id="summary-pendientes" class="text-3xl font-bold mt-3 text-[#0b1c30]">0</h3>
                </div>
                <div class="p-6 rounded-2xl border border-[#cbc3d5] bg-white">
                    <p class="text-sm text-[#494453]">Aprobadas</p>
                    <h3 id="summary-aprobadas" class="text-3xl font-bold mt-3 text-[#0b1c30]">0</h3>
                </div>
                <div class="p-6 rounded-2xl border border-[#cbc3d5] bg-white">
                    <p class="text-sm text-[#494453]">Rechazadas</p>
                    <h3 id="summary-rechazadas" class="text-3xl font-bold mt-3 text-[#0b1c30]">0</h3>
                </div>
            </div>

            <!-- Table section -->
            <div class="bg-white border border-[#cbc3d5] rounded-2xl shadow-sm overflow-x-auto">
                <table class="min-w-full text-left border-collapse">
                    <thead class="bg-[#eff4ff] text-[#494453] uppercase text-[11px] font-bold tracking-wider">
                        <tr>
                            <th class="px-6 py-4">#</th>
                            <th class="px-6 py-4">Cliente</th>
                            <th class="px-6 py-4">Fecha evento</th>
                            <th class="px-6 py-4">Tipo de Local</th>
                            <th class="px-6 py-4">Kit</th>
                            <th class="px-6 py-4">Estado</th>
                            <th class="px-6 py-4">Acciones</th>
                        </tr>
                    </thead>
                    <tbody id="solicitudes-table-body" class="divide-y divide-[#e2e8f0]">
                        <tr><td colspan="7" class="px-6 py-8 text-sm text-[#64748b] text-center">Cargando solicitudes...</td></tr>
                    </tbody>
                </table>
            </div>
        </div>
    `,

    // ----------------------------------------------------
    // 3. Eventos View (Listado real)
    // ----------------------------------------------------
    eventos: `
        <div>
            <div class="flex flex-col md:flex-row md:items-end justify-between gap-6 mb-10">
                <div>
                    <nav class="flex items-center gap-2 text-sm text-[#494453] mb-2">
                        <span>Dashboard</span>
                        <span class="material-symbols-outlined text-[16px]">chevron_right</span>
                        <span class="text-[#532aa8] font-semibold">Eventos</span>
                    </nav>
                    <h2 class="text-3xl font-bold text-[#0b1c30]">Eventos programados</h2>
                    <p class="text-[#494453] mt-1">Lista de eventos creados en el microservicio de eventos.</p>
                </div>
                <button id="refresh-eventos" class="flex items-center gap-2 bg-[#532aa8] text-white px-6 py-2.5 rounded-xl font-semibold hover:opacity-90 shadow-lg shadow-[#532aa8]/20 transition-all">
                    <span class="material-symbols-outlined">refresh</span>
                    Actualizar
                </button>
            </div>

            <div class="grid grid-cols-1 md:grid-cols-4 gap-6 mb-10">
                <div class="p-6 rounded-2xl border border-[#cbc3d5] bg-white">
                    <p class="text-sm text-[#494453]">Total eventos</p>
                    <h3 id="eventos-total" class="text-3xl font-bold mt-3 text-[#0b1c30]">0</h3>
                </div>
                <div class="p-6 rounded-2xl border border-[#cbc3d5] bg-white">
                    <p class="text-sm text-[#494453]">Confirmados</p>
                    <h3 id="eventos-confirmados" class="text-3xl font-bold mt-3 text-[#0b1c30]">0</h3>
                </div>
                <div class="p-6 rounded-2xl border border-[#cbc3d5] bg-white">
                    <p class="text-sm text-[#494453]">Cancelados</p>
                    <h3 id="eventos-cancelados" class="text-3xl font-bold mt-3 text-[#0b1c30]">0</h3>
                </div>
                <div class="p-6 rounded-2xl border border-[#cbc3d5] bg-white">
                    <p class="text-sm text-[#494453]">En curso</p>
                    <h3 id="eventos-en-curso" class="text-3xl font-bold mt-3 text-[#0b1c30]">0</h3>
                </div>
            </div>

            <div class="bg-white border border-[#cbc3d5] rounded-2xl shadow-sm overflow-x-auto">
                <table class="min-w-full text-left border-collapse">
                    <thead class="bg-[#eff4ff] text-[#494453] uppercase text-[11px] font-bold tracking-wider">
                        <tr>
                            <th class="px-6 py-4">#</th>
                            <th class="px-6 py-4">Cumpleañero</th>
                            <th class="px-6 py-4">Sucursal</th>
                            <th class="px-6 py-4">Tipo de Local</th>
                            <th class="px-6 py-4">Fecha</th>
                            <th class="px-6 py-4">Hora</th>
                            <th class="px-6 py-4">Estado</th>
                            <th class="px-6 py-4">Precio</th>
                        </tr>
                    </thead>
                    <tbody id="eventos-table-body" class="divide-y divide-[#e2e8f0]">
                        <tr><td colspan="8" class="px-6 py-8 text-sm text-[#64748b] text-center">Cargando eventos...</td></tr>
                    </tbody>
                </table>
            </div>
        </div>
    `,

    // ----------------------------------------------------
    // 4. Minutas View
    // ----------------------------------------------------
    minutas: `
        <div>
            <div class="flex flex-col md:flex-row md:items-end justify-between gap-6 mb-10">
                <div>
                    <nav class="flex items-center gap-2 text-sm text-[#494453] mb-2">
                        <span>Dashboard</span>
                        <span class="material-symbols-outlined text-[16px]">chevron_right</span>
                        <span class="text-[#532aa8] font-semibold">Minutas</span>
                    </nav>
                    <h2 class="text-3xl font-bold text-[#0b1c30]">Minutas operacionales</h2>
                    <p class="text-[#494453] mt-1">Visualice las minutas asociadas a cada evento.</p>
                </div>
                <button id="refresh-minutas" class="flex items-center gap-2 bg-[#532aa8] text-white px-6 py-2.5 rounded-xl font-semibold hover:opacity-90 shadow-lg shadow-[#532aa8]/20 transition-all">
                    <span class="material-symbols-outlined">refresh</span>
                    Actualizar
                </button>
            </div>

            <div class="bg-white border border-[#cbc3d5] rounded-2xl shadow-sm overflow-x-auto">
                <table class="min-w-full text-left border-collapse">
                    <thead class="bg-[#eff4ff] text-[#494453] uppercase text-[11px] font-bold tracking-wider">
                        <tr>
                            <th class="px-6 py-4">#</th>
                            <th class="px-6 py-4">Tipo de Local</th>
                            <th class="px-6 py-4">Fecha</th>
                            <th class="px-6 py-4">Hora</th>
                            <th class="px-6 py-4">Estado</th>
                            <th class="px-6 py-4">Minuta</th>
                        </tr>
                    </thead>
                    <tbody id="minutas-table-body" class="divide-y divide-[#e2e8f0]">
                        <tr><td colspan="6" class="px-6 py-8 text-sm text-[#64748b] text-center">Cargando eventos con minutas...</td></tr>
                    </tbody>
                </table>
            </div>

            <div id="minuta-detail" class="mt-8"></div>
        </div>
    `,

    // ----------------------------------------------------
    // 5. Reportes View
    // ----------------------------------------------------
    reportes: `
        <div>
            <div class="flex flex-col md:flex-row md:items-end justify-between gap-6 mb-10">
                <div>
                    <nav class="flex items-center gap-2 text-sm text-[#494453] mb-2">
                        <span>Dashboard</span>
                        <span class="material-symbols-outlined text-[16px]">chevron_right</span>
                        <span class="text-[#532aa8] font-semibold">Reportes</span>
                    </nav>
                    <h2 class="text-3xl font-bold text-[#0b1c30]">Reporte de operaciones</h2>
                    <p class="text-[#494453] mt-1">Métricas reales del microservicio de reportes.</p>
                </div>
                <button id="refresh-reportes" class="flex items-center gap-2 bg-[#532aa8] text-white px-6 py-2.5 rounded-xl font-semibold hover:opacity-90 shadow-lg shadow-[#532aa8]/20 transition-all">
                    <span class="material-symbols-outlined">refresh</span>
                    Actualizar
                </button>
            </div>

            <div class="grid grid-cols-1 md:grid-cols-4 gap-6 mb-10">
                <div class="p-6 rounded-2xl border border-[#cbc3d5] bg-white">
                    <p class="text-sm text-[#494453]">Eventos realizados</p>
                    <h3 id="reportes-eventos-realizados" class="text-3xl font-bold mt-3 text-[#0b1c30]">0</h3>
                </div>
                <div class="p-6 rounded-2xl border border-[#cbc3d5] bg-white">
                    <p class="text-sm text-[#494453]">Eventos cancelados</p>
                    <h3 id="reportes-eventos-cancelados" class="text-3xl font-bold mt-3 text-[#0b1c30]">0</h3>
                </div>
                <div class="p-6 rounded-2xl border border-[#cbc3d5] bg-white">
                    <p class="text-sm text-[#494453]">Solicitudes aprobadas</p>
                    <h3 id="reportes-solicitudes-aprobadas" class="text-3xl font-bold mt-3 text-[#0b1c30]">0</h3>
                </div>
                <div class="p-6 rounded-2xl border border-[#cbc3d5] bg-white">
                    <p class="text-sm text-[#494453]">Solicitudes rechazadas</p>
                    <h3 id="reportes-solicitudes-rechazadas" class="text-3xl font-bold mt-3 text-[#0b1c30]">0</h3>
                </div>
            </div>

            <div class="grid grid-cols-1 lg:grid-cols-2 gap-6">
                <div class="bg-white border border-[#cbc3d5] rounded-2xl shadow-sm overflow-x-auto">
                    <table class="min-w-full text-left border-collapse">
                        <thead class="bg-[#eff4ff] text-[#494453] uppercase text-[11px] font-bold tracking-wider">
                            <tr>
                                <th class="px-6 py-4">Sucursal</th>
                                <th class="px-6 py-4">Eventos</th>
                            </tr>
                        </thead>
                        <tbody id="reportes-sucursal-body" class="divide-y divide-[#e2e8f0]"></tbody>
                    </table>
                </div>
                <div class="bg-white border border-[#cbc3d5] rounded-2xl shadow-sm overflow-x-auto">
                    <table class="min-w-full text-left border-collapse">
                        <thead class="bg-[#eff4ff] text-[#494453] uppercase text-[11px] font-bold tracking-wider">
                            <tr>
                                <th class="px-6 py-4">Recurso</th>
                                <th class="px-6 py-4">Uso total</th>
                            </tr>
                        </thead>
                        <tbody id="reportes-recursos-body" class="divide-y divide-[#e2e8f0]"></tbody>
                    </table>
                </div>
            </div>

            <div class="bg-white border border-[#cbc3d5] rounded-2xl shadow-sm overflow-x-auto">
                <table class="min-w-full text-left border-collapse">
                    <thead class="bg-[#eff4ff] text-[#494453] uppercase text-[11px] font-bold tracking-wider">
                        <tr>
                            <th class="px-6 py-4">Personal</th>
                            <th class="px-6 py-4">Eventos participados</th>
                        </tr>
                    </thead>
                    <tbody id="reportes-personal-body" class="divide-y divide-[#e2e8f0]"></tbody>
                </table>
            </div>
        </div>
    `
};

const API_SOLICITUD_BASE = 'http://localhost:8082/api/v1';
const API_EVENTO_BASE = 'http://localhost:8085/api/v1';
const API_REPORTES_BASE = 'http://localhost:8086/api/v1';
const ViewLabels = {
    solicitudes: 'Solicitudes',
    eventos: 'Eventos',
    minutas: 'Minutas',
    reportes: 'Reportes'
};

function fetchJson(url, options = {}) {
    return fetch(url, options).then(async (response) => {
        if (!response.ok) {
            let message = response.statusText;
            try {
                const payload = await response.json();
                message = payload.message || payload.error || JSON.stringify(payload);
            } catch (e) {
                const text = await response.text();
                if (text) message = text;
            }
            throw new Error(message || `HTTP ${response.status}`);
        }
        return response.status === 204 ? null : response.json();
    });
}

function updateNavbarTitle(hash) {
    const navbar = document.querySelector('#global-navbar');
    if (navbar) {
        const title = ViewLabels[hash] || 'Solicitudes';
        navbar.setAttribute('title', title);
    }
}

async function router() {
    const hash = window.location.hash.slice(1) || 'solicitudes';
    console.log('[views.js] router() hash=', hash);
    const viewContent = Views[hash] || Views.solicitudes;
    const contentContainer = document.querySelector('#main-content > div');
    console.log('[views.js] router() contentContainer=', contentContainer);
    if (contentContainer) {
        contentContainer.innerHTML = viewContent;
    }
    updateNavbarTitle(hash);

    if (hash === 'solicitudes') await initSolicitudesView();
    if (hash === 'eventos') initEventosView();
    if (hash === 'minutas') initMinutasView();
    if (hash === 'reportes') initReportesView();
}

window.addEventListener('hashchange', router);
window.addEventListener('load', () => {
    if (!window.location.hash) {
        window.location.hash = '#solicitudes';
    } else {
        router();
    }
});

function formatDate(value) {
    if (!value) return '-';
    const date = new Date(value);
    return date.toLocaleDateString('es-CL', { day: '2-digit', month: '2-digit', year: 'numeric' });
}

function formatCurrency(value) {
    if (value == null || Number.isNaN(Number(value))) return '-';
    return Number(value).toLocaleString('es-CL', { style: 'currency', currency: 'CLP' });
}

function mapTipoLocal(nombreSala) {
    const mapping = {
        'Sala Piratas': 'Arcade',
        'Sala Mario Bros': 'Arcade',
        'Sala Princesas': 'Parque de trampolines',
        'Sala Aventuras': 'Parque de trampolines'
    };
    return mapping[nombreSala] || nombreSala || '-';
}

function createStatusBadge(status) {
    function mapVisualEstado(s) {
        const sNorm = String(s || '').toUpperCase();

        if (sNorm === 'PENDIENTE')
            return 'PENDIENTE';

        if (sNorm === 'CONFIRMADO' || sNorm === 'PROGRAMADO')
            return 'PROGRAMADO';

        if (sNorm === 'CANCELADO' || sNorm === 'RECHAZADO')
            return 'RECHAZADO';

        return sNorm;
    }

    const visual = mapVisualEstado(status);

    const classes = {
        PENDIENTE: 'bg-[#F59E0B]/10 text-[#B45309]',
        PROGRAMADO: 'bg-[#3B82F6]/10 text-[#1D4ED8]',
        RECHAZADO: 'bg-[#EF4444]/10 text-[#B91C1C]'
    };

    const badgeClass =
        classes[visual] || 'bg-[#E2E8F0]/80 text-[#475569]';

    return `<span class="px-3 py-1 rounded-full text-[11px] font-semibold ${badgeClass}">${visual}</span>`;
}
function showViewError(containerId, message) {
    const container = document.getElementById(containerId);
    if (!container) return;
    container.innerHTML = `<tr><td colspan="100" class="px-6 py-8 text-sm text-[#64748b] text-center">${message}</td></tr>`;
}

async function initSolicitudesView() {
    console.log('[views.js] Init solicitudes');
    const tableBody = document.getElementById('solicitudes-table-body');
    const refreshButton = document.querySelector('#refresh-solicitudes');
    const summaryTotal = document.getElementById('summary-total-solicitudes');
    const summaryPending = document.getElementById('summary-pendientes');
    const summaryApproved = document.getElementById('summary-aprobadas');
    const summaryRejected = document.getElementById('summary-rechazadas');

    console.log('[views.js] tableBody=', tableBody);
    console.log('[views.js] summaryTotal=', summaryTotal);
    console.log('[views.js] summaryPending=', summaryPending);
    console.log('[views.js] summaryApproved=', summaryApproved);
    console.log('[views.js] summaryRejected=', summaryRejected);

    if (refreshButton) {
        refreshButton.addEventListener('click', initSolicitudesView);
    }

    if (tableBody) {
        tableBody.innerHTML = '<tr><td colspan="7" class="px-6 py-8 text-sm text-[#64748b] text-center">Cargando solicitudes...</td></tr>';
    }

    try {
        const solicitudes = await fetchJson(`${API_SOLICITUD_BASE}/solicitudes`);
        const total = solicitudes.length;
        const pendientes = solicitudes.filter(s => s.estado === 'PENDIENTE').length;
        const aprobadas = solicitudes.filter(s => s.estado === 'CONFIRMADO').length;
        const rechazadas = solicitudes.filter(s => s.estado === 'CANCELADO').length;

        console.log('[views.js] Solicitudes recibidas:', solicitudes);
        console.log('[views.js] Cantidad:', solicitudes.length);
        console.log('[views.js] solicitudes instanceof Array:', Array.isArray(solicitudes));

        if (summaryTotal) summaryTotal.textContent = total;
        if (summaryPending) summaryPending.textContent = pendientes;
        if (summaryApproved) summaryApproved.textContent = aprobadas;
        if (summaryRejected) summaryRejected.textContent = rechazadas;

        if (!solicitudes.length) {
            showViewError('solicitudes-table-body', 'No se encontraron solicitudes.');
            return;
        }

        tableBody.innerHTML = solicitudes.map((solicitud, index) => {
            const fechaEvento = solicitud.fechaSolicitada ? formatDate(solicitud.fechaSolicitada) : '-';
            const horaInicio = solicitud.horaInicio ? solicitud.horaInicio.slice(0, 5) : '-';
            const horaFin = solicitud.horaFin ? solicitud.horaFin.slice(0, 5) : '-';
            const salaRaw = solicitud.nombreSala || solicitud.tipoSala || '-';
            const sala = mapTipoLocal(salaRaw);
            const kit = solicitud.nombreKit || '-';
            const actions = solicitud.estado === 'PENDIENTE'
                ? `<div class="flex flex-wrap gap-2">
                        <button data-action="confirm" data-id="${solicitud.idSolicitud}" class="px-3 py-2 text-xs font-semibold rounded-lg bg-[#10B981]/10 text-[#065f46] hover:bg-[#10B981]/20 transition">Aprobar</button>
                        <button data-action="reject" data-id="${solicitud.idSolicitud}" class="px-3 py-2 text-xs font-semibold rounded-lg bg-[#EF4444]/10 text-[#991B1B] hover:bg-[#EF4444]/20 transition">Rechazar</button>
                   </div>`
                : `<span class="text-sm text-[#64748b]">Sin acciones</span>`;

            return `
                <tr class="hover:bg-[#f8fafc] transition-colors">
                    <td class="px-6 py-4 text-sm font-semibold text-[#0b1c30]">${index + 1}</td>
                    <td class="px-6 py-4">
                        <div class="text-sm font-semibold text-[#0b1c30]">${solicitud.nombreCliente || '-'} ${solicitud.apellidoCliente || ''}</div>
                        <div class="text-xs text-[#64748b]">${solicitud.correoCliente || '-'} · ${solicitud.telefonoCliente || '-'}</div>
                    </td>
                    <td class="px-6 py-4 text-sm text-[#0b1c30]">${fechaEvento} · ${horaInicio} - ${horaFin}</td>
                    <td class="px-6 py-4 text-sm text-[#0b1c30]">${sala}</td>
                    <td class="px-6 py-4 text-sm text-[#0b1c30]">${kit}</td>
                    <td class="px-6 py-4">${createStatusBadge(solicitud.estado)}</td>
                    <td class="px-6 py-4">${actions}</td>
                </tr>`;
        }).join('');

        tableBody.querySelectorAll('button[data-action]').forEach(button => {
            button.addEventListener('click', async (event) => {
                const action = event.currentTarget.dataset.action;
                const id = event.currentTarget.dataset.id;
                const estado = action === 'confirm' ? 'CONFIRMADO' : 'CANCELADO';
                event.currentTarget.disabled = true;
                try {
                    await fetchJson(`${API_SOLICITUD_BASE}/solicitudes/${id}/estado`, {
                        method: 'PUT',
                        headers: { 'Content-Type': 'application/json' },
                        body: JSON.stringify({ estado })
                    });
                    initSolicitudesView();
                } catch (error) {
                    alert(`No se pudo actualizar la solicitud: ${error.message}`);
                } finally {
                    event.currentTarget.disabled = false;
                }
            });
        });
    } catch (error) {
        showViewError('solicitudes-table-body', `Error al cargar solicitudes: ${error.message}`);
        if (summaryTotal) summaryTotal.textContent = '0';
        if (summaryPending) summaryPending.textContent = '0';
        if (summaryApproved) summaryApproved.textContent = '0';
        if (summaryRejected) summaryRejected.textContent = '0';
    }
}

async function initEventosView() {
    const tableBody = document.getElementById('eventos-table-body');
    const refreshButton = document.querySelector('#refresh-eventos');
    const totalEl = document.getElementById('eventos-total');
    const confirmadosEl = document.getElementById('eventos-confirmados');
    const canceladosEl = document.getElementById('eventos-cancelados');
    const enCursoEl = document.getElementById('eventos-en-curso');

    if (refreshButton) {
        refreshButton.addEventListener('click', initEventosView);
    }

    if (tableBody) {
        tableBody.innerHTML = '<tr><td colspan="8" class="px-6 py-8 text-sm text-[#64748b] text-center">Cargando eventos...</td></tr>';
    }

    try {
        const eventos = await fetchJson(`${API_EVENTO_BASE}/eventos`);
        if (!Array.isArray(eventos) || eventos.length === 0) {
            showViewError('eventos-table-body', 'No se encontraron eventos.');
            if (totalEl) totalEl.textContent = '0';
            if (confirmadosEl) confirmadosEl.textContent = '0';
            if (canceladosEl) canceladosEl.textContent = '0';
            if (enCursoEl) enCursoEl.textContent = '0';
            return;
        }

        const confirmedCount = eventos.filter(evt => evt.estado === 'CONFIRMADO').length;
        const cancelledCount = eventos.filter(evt => evt.estado === 'CANCELADO').length;
        const runningCount = eventos.filter(evt => evt.estado === 'PROGRAMADO' || evt.estado === 'EN_CURSO').length;

        if (totalEl) totalEl.textContent = String(eventos.length);
        if (confirmadosEl) confirmadosEl.textContent = String(confirmedCount);
        if (canceladosEl) canceladosEl.textContent = String(cancelledCount);
        if (enCursoEl) enCursoEl.textContent = String(runningCount);

        tableBody.innerHTML = eventos.map((evento, index) => {
            const fecha = evento.fecha ? formatDate(evento.fecha) : '-';
            const horaInicio = evento.horaInicio ? evento.horaInicio.slice(0, 5) : '-';
            const horaFin = evento.horaFin ? evento.horaFin.slice(0, 5) : '-';
            const precio = typeof evento.precioTotal === 'number' ? evento.precioTotal.toLocaleString('es-CL', { style: 'currency', currency: 'CLP' }) : '-';

            const tipoLocal = mapTipoLocal(evento.nombreSala || '-');
            return `
                <tr class="hover:bg-[#f8fafc] transition-colors">
                    <td class="px-6 py-4 text-sm font-semibold text-[#0b1c30]">${index + 1}</td>
                    <td class="px-6 py-4 text-sm text-[#0b1c30]">${evento.nombreCumpleanero || '-'}</td>
                    <td class="px-6 py-4 text-sm text-[#0b1c30]">${evento.nombreSucursal || '-'}</td>
                    <td class="px-6 py-4 text-sm text-[#0b1c30]">${tipoLocal}</td>
                    <td class="px-6 py-4 text-sm text-[#0b1c30]">${fecha}</td>
                    <td class="px-6 py-4 text-sm text-[#0b1c30]">${horaInicio} - ${horaFin}</td>
                    <td class="px-6 py-4">${createStatusBadge(evento.estado)}</td>
                    <td class="px-6 py-4 text-sm text-[#0b1c30]">${precio}</td>
                </tr>`;
        }).join('');
    } catch (error) {
        showViewError('eventos-table-body', `Error al cargar eventos: ${error.message}`);
        if (totalEl) totalEl.textContent = '0';
        if (confirmadosEl) confirmadosEl.textContent = '0';
        if (canceladosEl) canceladosEl.textContent = '0';
        if (enCursoEl) enCursoEl.textContent = '0';
    }
}

async function initMinutasView() {
    const tableBody = document.getElementById('minutas-table-body');
    const refreshButton = document.querySelector('#refresh-minutas');
    const detailContainer = document.getElementById('minuta-detail');

    if (refreshButton) {
        refreshButton.addEventListener('click', initMinutasView);
    }

    if (tableBody) {
        tableBody.innerHTML = '<tr><td colspan="6" class="px-6 py-8 text-sm text-[#64748b] text-center">Cargando eventos...</td></tr>';
    }
    if (detailContainer) {
        detailContainer.innerHTML = '';
    }

    try {
        const eventos = await fetchJson(`${API_EVENTO_BASE}/eventos`);
        if (!Array.isArray(eventos) || eventos.length === 0) {
            showViewError('minutas-table-body', 'No se encontraron eventos.');
            return;
        }

        tableBody.innerHTML = eventos.map((evento, index) => {
            const tipoLocal = mapTipoLocal(evento.nombreSala || '-');
            const fecha = evento.fecha ? formatDate(evento.fecha) : '-';
            const horaInicio = evento.horaInicio ? evento.horaInicio.slice(0, 5) : '-';
            const horaFin = evento.horaFin ? evento.horaFin.slice(0, 5) : '-';
            const actionButton = `<button data-id="${evento.idEvento}" class="view-minuta inline-flex items-center gap-2 px-3 py-2 rounded-lg text-xs font-semibold bg-[#3B82F6]/10 text-[#1D4ED8] hover:bg-[#3B82F6]/20 transition">Generar minuta</button>`;

            return `
                <tr class="hover:bg-[#f8fafc] transition-colors">
                    <td class="px-6 py-4 text-sm font-semibold text-[#0b1c30]">${index + 1}</td>
                    <td class="px-6 py-4 text-sm text-[#0b1c30]">${tipoLocal}</td>
                    <td class="px-6 py-4 text-sm text-[#0b1c30]">${fecha}</td>
                    <td class="px-6 py-4 text-sm text-[#0b1c30]">${horaInicio} - ${horaFin}</td>
                    <td class="px-6 py-4">${createStatusBadge(evento.estado)}</td>
                    <td class="px-6 py-4">${actionButton}</td>
                </tr>`;
        }).join('');

        tableBody.querySelectorAll('.view-minuta').forEach(button => {
            button.addEventListener('click', async (event) => {
                const idEvento = event.currentTarget.dataset.id;
                await loadMinutaDetail(idEvento);
            });
        });
    } catch (error) {
        showViewError('minutas-table-body', `Error al cargar eventos: ${error.message}`);
    }
}

async function loadMinutaDetail(idEvento) {
    const detailContainer = document.getElementById('minuta-detail');
    if (!detailContainer) return;
    detailContainer.innerHTML = '<div class="bg-white border border-[#cbc3d5] rounded-2xl shadow-sm p-6 text-sm text-[#64748b]">Cargando minuta...</div>';

    try {
        const minuta = await fetchJson(`${API_EVENTO_BASE}/eventos/${idEvento}/minuta/detalle`);
        const fecha = minuta.fecha ? formatDate(minuta.fecha) : '-';
        const horaInicio = minuta.horaInicio ? minuta.horaInicio.slice(0, 5) : '-';
        const horaFin = minuta.horaFin ? minuta.horaFin.slice(0, 5) : '-';
        const precioBase = minuta.precioBaseKit != null ? formatCurrency(minuta.precioBaseKit) : '-';
        const precioPorNino = minuta.precioPorNinoKit != null ? formatCurrency(minuta.precioPorNinoKit) : '-';
        const precioTotalCalculado = minuta.precioTotalCalculado != null ? formatCurrency(minuta.precioTotalCalculado) : '-';

        detailContainer.innerHTML = `
            <div class="bg-white border border-[#cbc3d5] rounded-2xl shadow-sm p-6">
                <h3 class="text-xl font-semibold text-[#0b1c30] mb-4">Minuta del evento #${minuta.idEvento}</h3>

                <div class="grid grid-cols-1 lg:grid-cols-2 gap-6">
                    <div class="bg-[#f8fafc] border border-[#e2e8f0] rounded-2xl p-5">
                        <h4 class="text-sm font-semibold text-[#0b1c30] mb-3">DATOS DEL EVENTO</h4>
                        <p class="text-sm text-[#475569] mb-2"><span class="font-semibold text-[#0b1c30]">Fecha:</span> ${fecha}</p>
                        <p class="text-sm text-[#475569] mb-2"><span class="font-semibold text-[#0b1c30]">Hora inicio:</span> ${horaInicio}</p>
                        <p class="text-sm text-[#475569] mb-2"><span class="font-semibold text-[#0b1c30]">Hora fin:</span> ${horaFin}</p>
                        <p class="text-sm text-[#475569]"><span class="font-semibold text-[#0b1c30]">Estado:</span> ${minuta.estado || '-'}</p>
                    </div>

                    <div class="bg-[#f8fafc] border border-[#e2e8f0] rounded-2xl p-5">
                        <h4 class="text-sm font-semibold text-[#0b1c30] mb-3">DATOS DEL CUMPLEAÑERO</h4>
                        <p class="text-sm text-[#475569] mb-2"><span class="font-semibold text-[#0b1c30]">Nombre:</span> ${minuta.nombreCumpleanero || '-'}</p>
                        <p class="text-sm text-[#475569]"><span class="font-semibold text-[#0b1c30]">Edad:</span> ${minuta.edadCumpleanero != null ? minuta.edadCumpleanero : '-'}</p>
                    </div>
                </div>

                <div class="grid grid-cols-1 lg:grid-cols-2 gap-6 mt-6">
                    <div class="bg-[#f8fafc] border border-[#e2e8f0] rounded-2xl p-5">
                        <h4 class="text-sm font-semibold text-[#0b1c30] mb-3">DATOS DEL CLIENTE</h4>
                        <p class="text-sm text-[#475569] mb-2"><span class="font-semibold text-[#0b1c30]">Nombre completo:</span> ${minuta.nombreCliente || '-'} ${minuta.apellidoCliente || ''}</p>
                        <p class="text-sm text-[#475569] mb-2"><span class="font-semibold text-[#0b1c30]">Correo:</span> ${minuta.correoCliente || '-'}</p>
                        <p class="text-sm text-[#475569]"><span class="font-semibold text-[#0b1c30]">Teléfono:</span> ${minuta.telefonoCliente || '-'}</p>
                    </div>

                    <div class="bg-[#f8fafc] border border-[#e2e8f0] rounded-2xl p-5">
                        <h4 class="text-sm font-semibold text-[#0b1c30] mb-3">DETALLES DEL EVENTO</h4>
                        <p class="text-sm text-[#475569] mb-2"><span class="font-semibold text-[#0b1c30]">Cantidad de niños:</span> ${minuta.cantidadNinos != null ? minuta.cantidadNinos : '-'}</p>
                        <p class="text-sm text-[#475569] mb-2"><span class="font-semibold text-[#0b1c30]">Cantidad de adultos:</span> ${minuta.cantidadAdultos != null ? minuta.cantidadAdultos : '-'}</p>
                        <p class="text-sm text-[#475569]"><span class="font-semibold text-[#0b1c30]">Requerimientos especiales:</span> ${minuta.requerimientosEspeciales || '-'}</p>
                    </div>
                </div>

                <div class="bg-[#f8fafc] border border-[#e2e8f0] rounded-2xl p-5 mt-6">
                    <h4 class="text-sm font-semibold text-[#0b1c30] mb-3">KIT CONTRATADO</h4>
                    <p class="text-sm text-[#475569] mb-2"><span class="font-semibold text-[#0b1c30]">Nombre del kit:</span> ${minuta.nombreKit || '-'}</p>
                    <p class="text-sm text-[#475569] mb-2"><span class="font-semibold text-[#0b1c30]">Precio base:</span> ${precioBase}</p>
                    <p class="text-sm text-[#475569] mb-2"><span class="font-semibold text-[#0b1c30]">Precio por niño:</span> ${precioPorNino}</p>
                    <p class="text-sm text-[#475569]"><span class="font-semibold text-[#0b1c30]">Precio total calculado:</span> ${precioTotalCalculado}</p>
                </div>
            </div>`;
    } catch (error) {
        detailContainer.innerHTML = `<div class="bg-white border border-[#f87171]/20 rounded-2xl shadow-sm p-6 text-sm text-[#991b1b]">No fue posible cargar la minuta: ${error.message}</div>`;
    }
}

function parseJsonString(value) {
    if (!value) return null;
    try {
        return JSON.parse(value);
    } catch (error) {
        return value;
    }
}

function renderJsonBlock(jsonContent) {
    if (!jsonContent) {
        return '<p class="text-sm text-[#475569]">No hay contenido registrado.</p>';
    }
    if (typeof jsonContent === 'string') {
        return `<pre class="whitespace-pre-wrap text-[13px] text-[#475569]">${jsonContent}</pre>`;
    }
    return `<pre class="whitespace-pre-wrap text-[13px] text-[#475569]">${JSON.stringify(jsonContent, null, 2)}</pre>`;
}

async function initReportesView() {
    const refreshButton = document.querySelector('#refresh-reportes');
    const eventosRealizados = document.getElementById('reportes-eventos-realizados');
    const eventosCancelados = document.getElementById('reportes-eventos-cancelados');
    const solicitudesAprobadas = document.getElementById('reportes-solicitudes-aprobadas');
    const solicitudesRechazadas = document.getElementById('reportes-solicitudes-rechazadas');
    const sucursalBody = document.getElementById('reportes-sucursal-body');
    const recursosBody = document.getElementById('reportes-recursos-body');
    const personalBody = document.getElementById('reportes-personal-body');

    if (refreshButton) {
        refreshButton.addEventListener('click', initReportesView);
    }

    if (sucursalBody) {
        sucursalBody.innerHTML = '<tr><td colspan="2" class="px-6 py-8 text-sm text-[#64748b] text-center">Cargando reportes...</td></tr>';
    }
    if (recursosBody) {
        recursosBody.innerHTML = '<tr><td colspan="2" class="px-6 py-8 text-sm text-[#64748b] text-center">Cargando reportes...</td></tr>';
    }
    if (personalBody) {
        personalBody.innerHTML = '<tr><td colspan="2" class="px-6 py-8 text-sm text-[#64748b] text-center">Cargando reportes...</td></tr>';
    }

    try {
        const dashboard = await fetchJson(`${API_REPORTES_BASE}/reportes/dashboard`);
        if (eventosRealizados) eventosRealizados.textContent = String(dashboard.eventosRealizados || 0);
        if (eventosCancelados) eventosCancelados.textContent = String(dashboard.eventosCancelados || 0);
        if (solicitudesAprobadas) solicitudesAprobadas.textContent = String(dashboard.solicitudesAprobadas || 0);
        if (solicitudesRechazadas) solicitudesRechazadas.textContent = String(dashboard.solicitudesRechazadas || 0);

        sucursalBody.innerHTML = (dashboard.ocupacionPorSucursal || []).map(item => `
            <tr class="hover:bg-[#f8fafc] transition-colors">
                <td class="px-6 py-4 text-sm text-[#0b1c30]">${item.nombreSucursal || '-'}</td>
                <td class="px-6 py-4 text-sm text-[#0b1c30]">${item.cantidadEventos || 0}</td>
            </tr>`).join('') || '<tr><td colspan="2" class="px-6 py-8 text-sm text-[#64748b] text-center">No hay datos de ocupación.</td></tr>';

        recursosBody.innerHTML = (dashboard.recursosMasUtilizados || []).map(item => `
            <tr class="hover:bg-[#f8fafc] transition-colors">
                <td class="px-6 py-4 text-sm text-[#0b1c30]">${item.nombreRecurso || '-'}</td>
                <td class="px-6 py-4 text-sm text-[#0b1c30]">${item.cantidadTotal || 0}</td>
            </tr>`).join('') || '<tr><td colspan="2" class="px-6 py-8 text-sm text-[#64748b] text-center">No hay datos de recursos.</td></tr>';

        personalBody.innerHTML = (dashboard.personalMasUtilizado || []).map(item => `
            <tr class="hover:bg-[#f8fafc] transition-colors">
                <td class="px-6 py-4 text-sm text-[#0b1c30]">${item.nombrePersonal || '-'}</td>
                <td class="px-6 py-4 text-sm text-[#0b1c30]">${item.cantidadEventosParticipados || 0}</td>
            </tr>`).join('') || '<tr><td colspan="2" class="px-6 py-8 text-sm text-[#64748b] text-center">No hay datos de personal.</td></tr>';
    } catch (error) {
        const message = `Error al cargar reportes: ${error.message}`;
        if (sucursalBody) showViewError('reportes-sucursal-body', message);
        if (recursosBody) showViewError('reportes-recursos-body', message);
        if (personalBody) showViewError('reportes-personal-body', message);
        if (eventosRealizados) eventosRealizados.textContent = '0';
        if (eventosCancelados) eventosCancelados.textContent = '0';
        if (solicitudesAprobadas) solicitudesAprobadas.textContent = '0';
        if (solicitudesRechazadas) solicitudesRechazadas.textContent = '0';
    }
}
