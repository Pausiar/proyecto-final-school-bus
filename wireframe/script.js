// Bus Routes Configuration
const busRoutes = {
    bus1: {
        name: "Autobús 101",
        route: "Centro - Escuela Norte",
        path: [
            { x: 100, y: 400 },
            { x: 200, y: 350 },
            { x: 300, y: 250 },
            { x: 400, y: 200 },
            { x: 500, y: 100 }
        ],
        stops: [
            { name: "Parada 1 - Centro", x: 100, y: 400 },
            { name: "Parada 2 - Plaza", x: 200, y: 350 },
            { name: "Parada 3 - Parque", x: 300, y: 250 },
            { name: "Parada 4 - Avenida", x: 400, y: 200 },
            { name: "Escuela - Destino", x: 500, y: 100 }
        ]
    },
    bus2: {
        name: "Autobús 202",
        route: "Sur - Escuela Central",
        path: [
            { x: 80, y: 420 },
            { x: 180, y: 380 },
            { x: 280, y: 280 },
            { x: 380, y: 220 },
            { x: 480, y: 120 }
        ],
        stops: [
            { name: "Terminal Sur", x: 80, y: 420 },
            { name: "Mercado", x: 180, y: 380 },
            { name: "Hospital", x: 280, y: 280 },
            { name: "Universidad", x: 380, y: 220 },
            { name: "Escuela Central", x: 480, y: 120 }
        ]
    },
    bus3: {
        name: "Autobús 303",
        route: "Este - Escuela Oeste",
        path: [
            { x: 120, y: 380 },
            { x: 220, y: 330 },
            { x: 320, y: 230 },
            { x: 420, y: 180 },
            { x: 520, y: 80 }
        ],
        stops: [
            { name: "Barrio Este", x: 120, y: 380 },
            { name: "Centro Comercial", x: 220, y: 330 },
            { name: "Biblioteca", x: 320, y: 230 },
            { name: "Polideportivo", x: 420, y: 180 },
            { name: "Escuela Oeste", x: 520, y: 80 }
        ]
    },
    bus4: {
        name: "Autobús 404",
        route: "Norte - Escuela Sur",
        path: [
            { x: 90, y: 410 },
            { x: 190, y: 360 },
            { x: 290, y: 260 },
            { x: 390, y: 210 },
            { x: 490, y: 110 }
        ],
        stops: [
            { name: "Zona Norte", x: 90, y: 410 },
            { name: "Estación", x: 190, y: 360 },
            { name: "Ayuntamiento", x: 290, y: 260 },
            { name: "Teatro", x: 390, y: 210 },
            { name: "Escuela Sur", x: 490, y: 110 }
        ]
    }
};

// Global variables
let currentBus = null;
let animationFrame = null;
let currentPosition = 0;
let animationProgress = 0;

// DOM Elements
const busList = document.getElementById('busList');
const mapView = document.getElementById('mapView');
const backBtn = document.getElementById('backBtn');
const centerBtn = document.getElementById('centerBtn');
const busMarker = document.getElementById('busMarker');
const busTitle = document.getElementById('busTitle');
const searchInput = document.getElementById('searchInput');
const routePath = document.getElementById('routePath');

// Event Listeners
document.querySelectorAll('.bus-card').forEach(card => {
    card.addEventListener('click', () => {
        const busId = card.dataset.bus;
        showBusOnMap(busId);
    });
});

backBtn.addEventListener('click', () => {
    hideBusMap();
});

centerBtn.addEventListener('click', () => {
    centerMapOnBus();
});

searchInput.addEventListener('input', (e) => {
    filterBuses(e.target.value);
});

// Filter modal functionality
const filterBtn = document.getElementById('filterBtn');
const filterModal = document.getElementById('filterModal');
const closeModal = document.getElementById('closeModal');
const applyFilters = document.getElementById('applyFilters');
const resetFilters = document.getElementById('resetFilters');

filterBtn.addEventListener('click', () => {
    filterModal.classList.remove('hidden');
});

closeModal.addEventListener('click', () => {
    filterModal.classList.add('hidden');
});

filterModal.addEventListener('click', (e) => {
    if (e.target === filterModal) {
        filterModal.classList.add('hidden');
    }
});

applyFilters.addEventListener('click', () => {
    applyBusFilters();
    filterModal.classList.add('hidden');
});

resetFilters.addEventListener('click', () => {
    document.getElementById('filterActive').checked = true;
    document.getElementById('filterPaused').checked = true;
    document.getElementById('filterInactive').checked = true;
    document.querySelector('input[name="sort"][value="number"]').checked = true;
    document.getElementById('notifArrival').checked = true;
    document.getElementById('notifDelays').checked = false;
    applyBusFilters();
});

function applyBusFilters() {
    const showActive = document.getElementById('filterActive').checked;
    const showPaused = document.getElementById('filterPaused').checked;
    const showInactive = document.getElementById('filterInactive').checked;
    const sortBy = document.querySelector('input[name="sort"]:checked').value;
    
    const cards = Array.from(document.querySelectorAll('.bus-card'));
    
    // Filter by status
    cards.forEach(card => {
        const status = card.querySelector('.status');
        let shouldShow = true;
        
        if (status.classList.contains('active') && !showActive) {
            shouldShow = false;
        } else if (status.classList.contains('paused') && !showPaused) {
            shouldShow = false;
        } else if (status.classList.contains('inactive') && !showInactive) {
            shouldShow = false;
        }
        
        card.style.display = shouldShow ? 'flex' : 'none';
    });
    
    // Sort buses
    const visibleCards = cards.filter(card => card.style.display !== 'none');
    
    if (sortBy === 'number') {
        visibleCards.sort((a, b) => {
            const numA = parseInt(a.querySelector('h3').textContent.match(/\d+/)[0]);
            const numB = parseInt(b.querySelector('h3').textContent.match(/\d+/)[0]);
            return numA - numB;
        });
    } else if (sortBy === 'eta') {
        visibleCards.sort((a, b) => {
            const etaA = parseInt(a.querySelector('.eta').textContent.match(/\d+/)?.[0] || 999);
            const etaB = parseInt(b.querySelector('.eta').textContent.match(/\d+/)?.[0] || 999);
            return etaA - etaB;
        });
    } else if (sortBy === 'route') {
        visibleCards.sort((a, b) => {
            const routeA = a.querySelector('.route').textContent;
            const routeB = b.querySelector('.route').textContent;
            return routeA.localeCompare(routeB);
        });
    }
    
    // Re-append sorted cards
    visibleCards.forEach(card => busList.appendChild(card));
}

// Functions
function showBusOnMap(busId) {
    currentBus = busRoutes[busId];
    if (!currentBus) return;

    // Update title
    busTitle.textContent = currentBus.name;

    // Show map view
    busList.style.display = 'none';
    mapView.classList.remove('hidden');
    mapView.classList.add('fade-in');

    // Draw route
    drawRoute(currentBus.path);

    // Reset animation
    currentPosition = 0;
    animationProgress = 0;

    // Start bus animation
    animateBus();
    
    // Activate map button in navigation
    const navButtons = document.querySelectorAll('.nav-btn');
    navButtons.forEach(btn => btn.classList.remove('active'));
    navButtons[1].classList.add('active');
}

function showAllBusesOnMap() {
    // Update title
    busTitle.textContent = 'Mapa General';

    // Show map view
    busList.style.display = 'none';
    mapView.classList.remove('hidden');
    mapView.classList.add('fade-in');

    // Clear existing routes and buses
    document.getElementById('busRoute').setAttribute('d', '');
    
    // Create multiple bus markers
    const busMarkersContainer = document.getElementById('busMarkers');
    busMarkersContainer.innerHTML = '';
    
    // Draw all routes and buses
    const allPaths = [];
    Object.keys(busRoutes).forEach((busId, index) => {
        const bus = busRoutes[busId];
        allPaths.push(...bus.path);
        
        // Create bus marker
        const marker = document.createElement('div');
        marker.className = 'bus-marker';
        marker.id = `marker-${busId}`;
        marker.dataset.busId = busId;
        marker.innerHTML = `
            <div class="bus-pulse"></div>
            <div class="bus-icon">🚌</div>
            <div class="bus-name">${bus.name}</div>
        `;
        
        // Set initial position
        const startPos = bus.path[0];
        marker.style.left = startPos.x + 'px';
        marker.style.top = startPos.y + 'px';
        
        // Add click event to show individual bus
        marker.addEventListener('click', () => {
            showBusOnMap(busId);
        });
        
        busMarkersContainer.appendChild(marker);
    });
    
    // Start animation for all buses
    animateAllBuses();
    
    // Hide info panel in general view
    document.querySelector('.info-panel').style.display = 'none';
    
    // Activate map button in navigation
    const navButtons = document.querySelectorAll('.nav-btn');
    navButtons.forEach(btn => btn.classList.remove('active'));
    navButtons[1].classList.add('active');
}

function hideBusMap() {
    mapView.classList.add('hidden');
    busList.style.display = 'block';
    
    // Stop animation
    if (animationFrame) {
        cancelAnimationFrame(animationFrame);
        animationFrame = null;
    }
    
    currentBus = null;
    
    // Show info panel again
    document.querySelector('.info-panel').style.display = 'grid';
    
    // Activate home button in navigation
    const navButtons = document.querySelectorAll('.nav-btn');
    navButtons.forEach(btn => btn.classList.remove('active'));
    navButtons[0].classList.add('active');
}

function drawRoute(path) {
    if (path.length < 2) return;

    // Create smooth path using quadratic curves
    let pathData = `M ${path[0].x} ${path[0].y}`;
    
    for (let i = 0; i < path.length - 1; i++) {
        const current = path[i];
        const next = path[i + 1];
        const midX = (current.x + next.x) / 2;
        const midY = (current.y + next.y) / 2;
        
        if (i === 0) {
            pathData += ` L ${midX} ${midY}`;
        } else {
            pathData += ` Q ${current.x} ${current.y} ${midX} ${midY}`;
        }
    }
    
    pathData += ` L ${path[path.length - 1].x} ${path[path.length - 1].y}`;
    
    document.getElementById('busRoute').setAttribute('d', pathData);
}

let busAnimations = {};

function animateBus() {
    if (!currentBus) return;

    const path = currentBus.path;
    const totalSegments = path.length - 1;
    const segmentProgress = animationProgress % 1;
    const currentSegment = Math.floor(animationProgress) % totalSegments;
    
    const start = path[currentSegment];
    const end = path[(currentSegment + 1) % path.length];
    
    // Calculate current position with easing
    const easedProgress = easeInOutQuad(segmentProgress);
    const x = start.x + (end.x - start.x) * easedProgress;
    const y = start.y + (end.y - start.y) * easedProgress;
    
    // Update bus position
    const busMarker = document.getElementById('busMarker');
    if (busMarker) {
        busMarker.style.left = x + 'px';
        busMarker.style.top = y + 'px';
        
        // Calculate rotation angle
        const angle = Math.atan2(end.y - start.y, end.x - start.x) * 180 / Math.PI;
        busMarker.style.transform = `translate(-50%, -50%) rotate(${angle + 90}deg)`;
    }
    
    // Update info panel
    updateInfoPanel(currentSegment, segmentProgress);
    
    // Increment progress (slower for smoother animation)
    animationProgress += 0.003;
    
    // Loop animation
    if (animationProgress >= totalSegments) {
        animationProgress = 0;
    }
    
    animationFrame = requestAnimationFrame(animateBus);
}

function animateAllBuses() {
    // Initialize progress for each bus
    Object.keys(busRoutes).forEach((busId, index) => {
        if (!busAnimations[busId]) {
            busAnimations[busId] = index * 0.3; // Stagger start positions
        }
    });
    
    function animate() {
        Object.keys(busRoutes).forEach((busId) => {
            const bus = busRoutes[busId];
            const path = bus.path;
            const totalSegments = path.length - 1;
            const progress = busAnimations[busId];
            const segmentProgress = progress % 1;
            const currentSegment = Math.floor(progress) % totalSegments;
            
            const start = path[currentSegment];
            const end = path[(currentSegment + 1) % path.length];
            
            // Calculate current position with easing
            const easedProgress = easeInOutQuad(segmentProgress);
            const x = start.x + (end.x - start.x) * easedProgress;
            const y = start.y + (end.y - start.y) * easedProgress;
            
            // Update bus position
            const marker = document.getElementById(`marker-${busId}`);
            if (marker) {
                marker.style.left = x + 'px';
                marker.style.top = y + 'px';
            }
            
            // Increment progress
            busAnimations[busId] += 0.002 + (index * 0.0002); // Slightly different speeds
            
            // Loop animation
            if (busAnimations[busId] >= totalSegments) {
                busAnimations[busId] = 0;
            }
        });
        
        animationFrame = requestAnimationFrame(animate);
    }
    
    animate();
}

function easeInOutQuad(t) {
    return t < 0.5 ? 2 * t * t : -1 + (4 - 2 * t) * t;
}

// Variable to store passenger count
let currentPassengers = 28;
let lastStopSegment = -1;

function updateInfoPanel(segment, progress) {
    if (!currentBus) return;

    const stops = currentBus.stops;
    const nextStopIndex = (segment + 1) % stops.length;
    
    // Update speed (varies slightly for realism)
    const baseSpeed = 45;
    const speedVariation = Math.sin(Date.now() / 1000) * 5;
    const speed = Math.round(baseSpeed + speedVariation);
    document.getElementById('speedValue').textContent = speed + ' km/h';
    
    // Update next stop
    document.getElementById('nextStop').textContent = stops[nextStopIndex].name;
    
    // Update passengers ONLY when reaching a new stop (segment changes)
    if (segment !== lastStopSegment && progress < 0.1) {
        // Change passengers: some get off, some get on
        const change = Math.floor(Math.random() * 6) - 3; // -3 to +2
        currentPassengers = Math.max(15, Math.min(40, currentPassengers + change));
        lastStopSegment = segment;
    }
    document.getElementById('passengers').textContent = currentPassengers + '/40';
    
    // Update ETA
    const remainingSegments = (stops.length - 1 - segment) + (1 - progress);
    const eta = Math.ceil(remainingSegments * 2); // 2 minutes per segment
    document.getElementById('etaValue').textContent = eta + ' min';
}

function centerMapOnBus() {
    const mapContainer = document.querySelector('.map-container');
    if (!mapContainer) return;
    
    let targetX, targetY;
    
    if (currentBus) {
        // Single bus view - center on the active bus
        const busMarker = document.getElementById('busMarker');
        if (!busMarker) return;
        targetX = parseInt(busMarker.style.left) || 0;
        targetY = parseInt(busMarker.style.top) || 0;
    } else {
        // Multiple buses view - center on map center
        targetX = 400;
        targetY = 300;
    }
    
    // Get container dimensions
    const containerWidth = mapContainer.clientWidth;
    const containerHeight = mapContainer.clientHeight;
    
    // Calculate scroll position to center
    const scrollLeft = targetX - (containerWidth / 2);
    const scrollTop = targetY - (containerHeight / 2);
    
    // Smooth scroll to center
    mapContainer.scrollTo({
        left: scrollLeft,
        top: scrollTop,
        behavior: 'smooth'
    });
}

function filterBuses(searchTerm) {
    const cards = document.querySelectorAll('.bus-card');
    const term = searchTerm.toLowerCase();
    
    cards.forEach(card => {
        const busInfo = card.querySelector('.bus-info').textContent.toLowerCase();
        if (busInfo.includes(term)) {
            card.style.display = 'flex';
        } else {
            card.style.display = 'none';
        }
    });
}

// Update times dynamically
function updateETAs() {
    const etaElements = document.querySelectorAll('.eta');
    etaElements.forEach((eta, index) => {
        if (eta.textContent.includes('min')) {
            const currentTime = parseInt(eta.textContent);
            if (currentTime > 1 && Math.random() > 0.7) {
                eta.textContent = `Llegada estimada: ${currentTime - 1} min`;
            }
        }
    });
}

// Update ETAs every 10 seconds
setInterval(updateETAs, 10000);

// Add random status updates
function updateBusStatuses() {
    const statusElements = document.querySelectorAll('.status');
    statusElements.forEach(status => {
        if (Math.random() > 0.95) {
            if (status.classList.contains('active')) {
                // Occasionally change to paused
                status.classList.remove('active');
                status.classList.add('paused');
                status.textContent = '● Parada';
            } else if (status.classList.contains('paused')) {
                // Return to active
                status.classList.remove('paused');
                status.classList.add('active');
                status.textContent = '● En ruta';
            }
        }
    });
}

// Update statuses occasionally
setInterval(updateBusStatuses, 15000);

// Bottom navigation functionality
const navButtons = document.querySelectorAll('.nav-btn');

navButtons.forEach((btn, index) => {
    btn.addEventListener('click', function() {
        // Remove active class from all buttons
        navButtons.forEach(b => b.classList.remove('active'));
        // Add active class to clicked button
        this.classList.add('active');
        
        // Handle different sections
        const label = this.querySelector('.nav-label').textContent;
        
        if (label === 'Inicio') {
            showHomeView();
        } else if (label === 'Mapa') {
            showMapListView();
        } else if (label === 'Favoritos') {
            showFavoritesView();
        }
    });
});

function showHomeView() {
    // Hide map view if open
    if (!mapView.classList.contains('hidden')) {
        hideBusMap();
    }
    // Show all buses
    document.querySelectorAll('.bus-card').forEach(card => {
        card.style.display = 'flex';
    });
    searchInput.value = '';
}

function showMapListView() {
    // Show all buses on map
    showAllBusesOnMap();
}

function showFavoritesView() {
    // Hide map view if open
    if (!mapView.classList.contains('hidden')) {
        hideBusMap();
    }
    // Show only favorite buses (bus1 and bus2 as favorites)
    document.querySelectorAll('.bus-card').forEach(card => {
        const busId = card.dataset.bus;
        if (busId === 'bus1' || busId === 'bus2') {
            card.style.display = 'flex';
        } else {
            card.style.display = 'none';
        }
    });
}

// Add click animation to cards
document.querySelectorAll('.bus-card').forEach(card => {
    card.addEventListener('mousedown', function() {
        this.style.transform = 'scale(0.98)';
    });
    
    card.addEventListener('mouseup', function() {
        this.style.transform = '';
    });
});

// Notifications panel functionality
const notificationIcon = document.getElementById('notificationIcon');
const notificationsPanel = document.getElementById('notificationsPanel');
const notificationBadge = document.getElementById('notificationBadge');
const clearAllNotifications = document.getElementById('clearAllNotifications');
const notificationsList = document.getElementById('notificationsList');

let unreadCount = 3;

notificationIcon.addEventListener('click', (e) => {
    e.stopPropagation();
    notificationsPanel.classList.toggle('hidden');
    
    // Mark all as read when opening
    if (!notificationsPanel.classList.contains('hidden')) {
        setTimeout(() => {
            document.querySelectorAll('.notification-item.unread').forEach(item => {
                item.classList.remove('unread');
            });
            unreadCount = 0;
            updateNotificationBadge();
        }, 1000);
    }
});

// Close panel when clicking outside
document.addEventListener('click', (e) => {
    if (!notificationsPanel.contains(e.target) && !notificationIcon.contains(e.target)) {
        notificationsPanel.classList.add('hidden');
    }
});

// Clear all notifications
clearAllNotifications.addEventListener('click', () => {
    notificationsList.innerHTML = '<div style="text-align: center; padding: 40px; color: #999;"><p>No hay notificaciones</p></div>';
    unreadCount = 0;
    updateNotificationBadge();
});

// Mark notification as read on click
notificationsList.addEventListener('click', (e) => {
    const notificationItem = e.target.closest('.notification-item');
    if (notificationItem && notificationItem.classList.contains('unread')) {
        notificationItem.classList.remove('unread');
        unreadCount = Math.max(0, unreadCount - 1);
        updateNotificationBadge();
    }
});

function updateNotificationBadge() {
    if (unreadCount > 0) {
        notificationBadge.textContent = unreadCount;
        notificationBadge.style.display = 'block';
    } else {
        notificationBadge.style.display = 'none';
    }
}

// Add new notification
function addNotification(type, title, message) {
    // Remove "no notifications" message if exists
    const emptyMessage = notificationsList.querySelector('div[style]');
    if (emptyMessage) {
        notificationsList.innerHTML = '';
    }
    
    const icons = {
        arrival: '🚌',
        delay: '⏱️',
        stop: '✅',
        active: '🚀'
    };
    
    const notification = document.createElement('div');
    notification.className = 'notification-item unread';
    notification.innerHTML = `
        <div class="notification-icon-badge">${icons[type] || '🔔'}</div>
        <div class="notification-content">
            <h4>${title}</h4>
            <p>${message}</p>
            <span class="notification-time">Ahora</span>
        </div>
    `;
    
    notificationsList.insertBefore(notification, notificationsList.firstChild);
    
    unreadCount++;
    updateNotificationBadge();
    
    // Animate notification icon
    notificationIcon.style.animation = 'none';
    setTimeout(() => {
        notificationIcon.style.animation = 'bellRing 0.5s ease';
    }, 10);
}

// Simulate real-time notifications
setInterval(() => {
    if (Math.random() > 0.85) {
        const notifications = [
            { type: 'arrival', title: 'Autobús 101 llegando', message: 'Llegará en 3 minutos a tu parada' },
            { type: 'delay', title: 'Retraso detectado', message: 'Autobús 202 retrasado 2 minutos' },
            { type: 'stop', title: 'Autobús en parada', message: 'Autobús 303 detenido en Hospital' },
            { type: 'active', title: 'Servicio iniciado', message: 'Autobús 404 comenzó su ruta' }
        ];
        
        const random = notifications[Math.floor(Math.random() * notifications.length)];
        addNotification(random.type, random.title, random.message);
    }
}, 25000);

updateNotificationBadge();

// Drag to scroll functionality for map
let isDragging = false;
let startX, startY;
let scrollLeft, scrollTop;

const mapContainer = document.querySelector('.map-container');

if (mapContainer) {
    mapContainer.addEventListener('mousedown', (e) => {
        // Ignore if clicking on buttons or interactive elements
        if (e.target.closest('button') || e.target.closest('.stop-label')) {
            return;
        }
        
        isDragging = true;
        mapContainer.classList.add('dragging');
        startX = e.pageX - mapContainer.offsetLeft;
        startY = e.pageY - mapContainer.offsetTop;
        scrollLeft = mapContainer.scrollLeft;
        scrollTop = mapContainer.scrollTop;
    });

    mapContainer.addEventListener('mouseleave', () => {
        isDragging = false;
        mapContainer.classList.remove('dragging');
    });

    mapContainer.addEventListener('mouseup', () => {
        isDragging = false;
        mapContainer.classList.remove('dragging');
    });

    mapContainer.addEventListener('mousemove', (e) => {
        if (!isDragging) return;
        e.preventDefault();
        const x = e.pageX - mapContainer.offsetLeft;
        const y = e.pageY - mapContainer.offsetTop;
        const walkX = (x - startX) * 1.5; // Multiplier for scroll speed
        const walkY = (y - startY) * 1.5;
        mapContainer.scrollLeft = scrollLeft - walkX;
        mapContainer.scrollTop = scrollTop - walkY;
    });

    // Touch support for mobile
    let touchStartX, touchStartY;
    
    mapContainer.addEventListener('touchstart', (e) => {
        const touch = e.touches[0];
        touchStartX = touch.pageX;
        touchStartY = touch.pageY;
        scrollLeft = mapContainer.scrollLeft;
        scrollTop = mapContainer.scrollTop;
    });

    mapContainer.addEventListener('touchmove', (e) => {
        const touch = e.touches[0];
        const walkX = (touch.pageX - touchStartX);
        const walkY = (touch.pageY - touchStartY);
        mapContainer.scrollLeft = scrollLeft - walkX;
        mapContainer.scrollTop = scrollTop - walkY;
    });
}

// Initialize
console.log('School Bus Tracker Wireframe loaded successfully! 🚌');
console.log('Click on any bus card to see it animated on the map.');
console.log('Drag to move around the map! 🗺️');
