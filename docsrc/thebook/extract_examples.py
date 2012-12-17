with open('./test/docbook/test/DocbookExamplesTest.java') as f1:
  f2 = None
  for line in f1:
    if line.startswith('\t}'):
      if f2:
        f2.write(']]>')
        f2.close()
        f2 = None
    if f2 and '// IGNORE' not in line:
      f2.write(line[2:].replace('\t','    '))
    if line.startswith('\tpublic void test'):
      c = line[len('\tpublic void '):line.rfind('(')]
      f2 = open('gen/docbook/%s.jva' % c, 'w')
      f2.write('<![CDATA[')

