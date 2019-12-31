import os

def main():
	walls = []
	slabs = []
	stairs = []
	buttons = []
	fence_gates = []

	for file in os.listdir('../assets/quark/blockstates'):
		if '.json' in file:
			name = file.replace('.json', '')
			if '_wall' in name:
				walls.append(name)
			elif ('_slab' in name) and not ('_vertical' in name):
				slabs.append(name)
			elif '_stairs' in name:
				stairs.append(name)
			elif '_button' in name:
				buttons.append(name)
			elif '_fence_gate' in name:
				fence_gates.append(name)

	write_file('items/walls.json', walls)
	write_file('items/slabs.json', slabs)
	write_file('items/stairs.json', stairs)
	write_file('items/buttons.json', buttons)
	write_file('items/fence_gates.json', fence_gates, domain='forge')

	write_file('blocks/walls.json', walls)
	write_file('blocks/slabs.json', slabs)
	write_file('blocks/stairs.json', stairs)
	write_file('blocks/buttons.json', buttons)
	write_file('blocks/fence_gates.json', fence_gates, domain='forge')

def write_file(filename, arr, domain='minecraft'):
	first = True
	with open('../data/' + domain + '/tags/' + filename, 'w') as writer:
		writer.write('{\n')
		writer.write('  "replace": false,\n')
		writer.write('  "values": [\n')
		for name in arr:
			if not first:
				writer.write(',\n')
			else:
				first = False
			writer.write('    "quark:' + name + '"')
		writer.write('\n  ]\n')
		writer.write('}\n')

main()
