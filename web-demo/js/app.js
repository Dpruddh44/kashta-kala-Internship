const furnitureData = [
    { title: "Modern Teak Sofa", category: "sofa", size: "tall", img: "https://images.unsplash.com/photo-1555041469-a586c61ea9bc?auto=format&fit=crop&w=400&q=80" },
    { title: "Minimalist Bed", category: "bed", size: "short", img: "https://images.unsplash.com/photo-1505693419173-42b9218a5c10?auto=format&fit=crop&w=400&q=80" },
    { title: "Walnut Cabinet", category: "cabinet", size: "tall", img: "https://images.unsplash.com/photo-1595428774223-ef52624120d2?auto=format&fit=crop&w=400&q=80" },
    { title: "Glass Dining Table", category: "table", size: "tall", img: "https://images.unsplash.com/photo-1530018607912-eff2df114f11?auto=format&fit=crop&w=400&q=80" },
    { title: "Scandinavian Chair", category: "chair", size: "short", img: "https://images.unsplash.com/photo-1567538096630-e0c55bd6374c?auto=format&fit=crop&w=400&q=80" },
    { title: "Industrial Shelf", category: "cabinet", size: "tall", img: "https://images.unsplash.com/photo-1594620302200-9a762244a156?auto=format&fit=crop&w=400&q=80" },
    { title: "Luxury Velvet Sofa", category: "sofa", size: "short", img: "https://images.unsplash.com/photo-1493663284031-b7e3aefcae8e?auto=format&fit=crop&w=400&q=80" },
    { title: "Oak Office Desk", category: "table", size: "tall", img: "https://images.unsplash.com/photo-1518455027359-f3f816b1a238?auto=format&fit=crop&w=400&q=80" },
];

const grid = document.getElementById('grid');

function renderGrid(filter = 'all') {
    grid.innerHTML = '';
    const filtered = filter === 'all' ? furnitureData : furnitureData.filter(item => item.category === filter);
    
    filtered.forEach(item => {
        const card = document.createElement('div');
        card.className = `card ${item.size}`;
        card.innerHTML = `
            <img src="${item.img}" alt="${item.title}">
            <div class="card-overlay">
                <h4>${item.title}</h4>
            </div>
        `;
        grid.appendChild(card);
    });
}

// ── Filter Logic ──
document.querySelectorAll('.filter-chip').forEach(chip => {
    chip.addEventListener('click', () => {
        document.querySelector('.filter-chip.active').classList.remove('active');
        chip.classList.add('active');
        renderGrid(chip.dataset.filter);
    });
});

// ── Workbench Calculation ──
const lengthInput = document.getElementById('length');
const widthInput = document.getElementById('width');
const timberSelect = document.getElementById('timber');
const calcBtn = document.getElementById('calc-btn');

const resSqft = document.getElementById('res-sqft');
const resCost = document.getElementById('res-cost');

function calculate() {
    const l = parseFloat(lengthInput.value) || 0;
    const w = parseFloat(widthInput.value) || 0;
    const rate = parseFloat(timberSelect.value);

    // cm^2 to sq.ft conversion: (L * W) / 929.03
    const sqft = (l * w) / 929.03;
    const cost = sqft * rate;

    // Animate numbers
    animateValue(resSqft, parseFloat(resSqft.innerText), sqft, 1000, true);
    animateValue(resCost, parseInt(resCost.innerText), Math.round(cost), 1000, false);
}

function animateValue(obj, start, end, duration, isFloat) {
    let startTimestamp = null;
    const step = (timestamp) => {
        if (!startTimestamp) startTimestamp = timestamp;
        const progress = Math.min((timestamp - startTimestamp) / duration, 1);
        const current = progress * (end - start) + start;
        obj.innerHTML = isFloat ? current.toFixed(2) : Math.floor(current);
        if (progress < 1) {
            window.requestAnimationFrame(step);
        }
    };
    window.requestAnimationFrame(step);
}

calcBtn.addEventListener('click', calculate);

// ── Init ──
renderGrid();
calculate(); // Initial calc
